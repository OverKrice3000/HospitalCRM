package com.hoscrm.Patient;

import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Exceptions.NotNullParameterAbsentException;
import com.hoscrm.Exceptions.UnexpectedUrlParameterException;
import com.hoscrm.Validators.NotNullParameterInRequestValidator;
import com.hoscrm.annotations.ReceiveNotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    public ResponseEntity<?> addPatient(@RequestBody Patient patient){
        try{
            NotNullParameterInRequestValidator.validate(patient);
            Patient created = service.addPatient(patient);
            return ResponseEntity.ok().body(created);
        } catch(NotNullParameterAbsentException | ConstraintViolationException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
    }

    @PutMapping(name="update",
                path="/update",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePatient(@RequestParam(required = true) Long id, @RequestBody Patient patient){
        patient.setId(id);
        try{
            NotNullParameterInRequestValidator.validate(patient);
            Patient retPs = service.updatePatient(patient);
            return ResponseEntity.ok(retPs);
        } catch(NoSuchElementInDatabaseException | NotNullParameterAbsentException | ConstraintViolationException e){
            return ResponseEntity.status(400).body(Map.of("Reason", e.getMessage()));
        }
    }

    @DeleteMapping(name="delete",
                    path="/delete"
    )
    public ResponseEntity<?> deletePatient(@RequestParam(required = true) Long id){
        try{
            service.deletePatientById(id);
            return ResponseEntity.ok().build();
        } catch(NoSuchElementInDatabaseException e) {
            return ResponseEntity.status(400).body(Map.of("Reason", e.getMessage()));
        }
    }

}
