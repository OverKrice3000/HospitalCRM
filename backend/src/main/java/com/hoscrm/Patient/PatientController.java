package com.hoscrm.Patient;

import com.hoscrm.Exceptions.NotNullParameterAbsentException;
import com.hoscrm.Exceptions.UnexpectedUrlParameterException;
import com.hoscrm.annotations.ReceiveNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(name="PatientController",
        path="/api/patient")
public class PatientController {
    private PatientService service;

    @Resource
    private HttpServletRequest request;

    public PatientController(PatientService patientService){
        this.service = patientService;
    }

    @GetMapping(name="find",
                path="/find",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> findPatients(@RequestParam(name="firstname", required = false) String firstName,
                                                        @RequestParam(name="lastname", required = false) String lastName,
                                                        @RequestParam(name="age", required = false) Short age){
        try{
            request.getParameterMap().forEach((s, ss) -> {
                if(!List.of("firstname",
                        "lastname",
                        "age").contains(s))
                    throw new UnexpectedUrlParameterException("Unexpected url parameter: " + s);
            });
        } catch (UnexpectedUrlParameterException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
        return ResponseEntity.ok(service.findPatients(firstName, lastName, age));
    }

    @GetMapping(name="get",
                path="/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPatient(@PathVariable(name="id") Long id){
        Optional<Patient> patient = service.getPatientById(id);
        if(patient.isEmpty())
            return ResponseEntity.ok().body(List.of());
        return ResponseEntity.ok(List.of(patient.get()));
    }

    @PostMapping(name="add",
                 path="/add",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> addPatient(@RequestBody() Patients patients){
        try{
            patients.patients.forEach(p -> {
                p.setId(null);
                for(Field f: p.getClass().getDeclaredFields()){
                    boolean accessible = f.canAccess(p);
                    f.setAccessible(true);
                    try {
                        if(f.getAnnotation(ReceiveNotNull.class) != null && f.get(p)== null){
                            f.setAccessible(accessible);
                            throw new NotNullParameterAbsentException("Mandatory parameter is missing:  " + f.getName());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(NotNullParameterAbsentException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
        List<Patient> created = service.addPatients(patients.patients);
        return ResponseEntity.ok().body(created);
    }

    @DeleteMapping(name="delete",
                    path="/delete"
    )
    public ResponseEntity<?> deletePatient(@RequestBody Ids ids){
        service.deletePatientsByIds(ids.ids);
        return ResponseEntity.ok().build();
    }

    static class Ids{
        List<Long> ids;

        public Ids(){}

        public Ids(List<Long> ids) {
            this.ids = ids;
        }

        public List<Long> getIds() {
            return ids;
        }

        public void setIds(List<Long> ids) {
            this.ids = ids;
        }
    }

    static class Patients{
        List<Patient> patients;

        Patients(){}

        public Patients(List<Patient> patients) {
            this.patients = patients;
        }

        public List<Patient> getPatients() {
            return patients;
        }

        public void setPatients(List<Patient> patients) {
            this.patients = patients;
        }
    }

}
