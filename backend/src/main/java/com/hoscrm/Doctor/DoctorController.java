package com.hoscrm.Doctor;

import com.hoscrm.Exceptions.NotNullParameterAbsentException;
import com.hoscrm.Exceptions.UnexpectedUrlParameterException;
import com.hoscrm.Patient.Patient;
import com.hoscrm.Patient.PatientController;
import com.hoscrm.annotations.ReceiveNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path="/api/doctor")
public class DoctorController {

    private DoctorService service;
    @Resource
    private HttpServletRequest request;

    public DoctorController(DoctorService service){
        this.service = service;
    }

    @GetMapping(name="find",
            path="/find",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> findDoctors(@RequestParam(name="firstname", required = false) String firstName,
                                         @RequestParam(name="lastname", required = false) String lastName,
                                         @RequestParam(name="speciality", required = false) String speciality,
                                         @RequestParam(name="salary", required = false) Integer minimalSalary) throws NoSuchMethodException {
        try{
            request.getParameterMap().forEach((s, ss) -> {
                if(!List.of("firstname",
                        "lastname",
                        "speciality",
                        "salary").contains(s))
                    throw new UnexpectedUrlParameterException("Unexpected url parameter: " + s);
            });
        } catch (UnexpectedUrlParameterException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
        return ResponseEntity.ok(service.findDoctors(firstName, lastName, speciality, minimalSalary));
    }


    @GetMapping(name="get",
            path="/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDoctor(@PathVariable(name="id") Long id){
        Optional<Doctor> doctor = service.getDoctorById(id);
        if(doctor.isEmpty())
            return ResponseEntity.ok().body(List.of());
        return ResponseEntity.ok(List.of(doctor.get()));
    }

    @PostMapping(name="add",
            path="/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> addDoctor(@RequestBody() Doctors doctors){
        try{
            doctors.doctors.forEach(p -> {
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
        List<Doctor> created = service.addDoctors(doctors.doctors);
        return ResponseEntity.ok().body(created);
    }

    @PutMapping(name="update",
            path="/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDoctors(@RequestBody Doctors doctors){
        List<Doctor> retPs = service.updateDoctors(doctors.doctors);
        return (retPs == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Some patients do not exist in database!")) :
                ResponseEntity.ok(retPs);
    }

    @DeleteMapping(name="delete",
            path="/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteDoctor(@RequestBody Ids ids){
        boolean deleted = service.deleteDoctorsByIds(ids.ids);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.status(400).body(Map.of("Reason", "Some patients do not exist in database!"));
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

    static class Doctors{
        List<Doctor> doctors;

        Doctors(){}

        public Doctors(List<Doctor> doctors) {
            this.doctors = doctors;
        }

        public List<Doctor> getDoctors() {
            return doctors;
        }

        public void setDoctors(List<Doctor> doctors) {
            this.doctors = doctors;
        }
    }


}
