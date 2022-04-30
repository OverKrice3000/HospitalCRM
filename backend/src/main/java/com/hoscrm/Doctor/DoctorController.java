package com.hoscrm.Doctor;

import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Exceptions.NotNullParameterAbsentException;
import com.hoscrm.Exceptions.UnexpectedUrlParameterException;
import com.hoscrm.Validators.NotNullParameterInRequestValidator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path="/api/doctor")
public class DoctorController {

    private DoctorServiceImpl service;
    @Resource
    private HttpServletRequest request;

    public DoctorController(DoctorServiceImpl service){
        this.service = service;
    }

    @GetMapping(name="find",
            path="/find",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> findDoctors(@RequestParam(name="firstname", required = false) String firstName,
                                         @RequestParam(name="lastname", required = false) String lastName,
                                         @RequestParam(name="speciality", required = false) String speciality,
                                         @RequestParam(name="salary", required = false) Integer minimalSalary,
                                         @RequestParam(name="department", required = false) String department) throws NoSuchMethodException {
        try{
            request.getParameterMap().forEach((s, ss) -> {
                if(!List.of("firstname",
                        "lastname",
                        "speciality",
                        "salary",
                        "department").contains(s))
                    throw new UnexpectedUrlParameterException("Unexpected url parameter: " + s);
            });
        } catch (UnexpectedUrlParameterException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
        return ResponseEntity.ok(service.findDoctors(firstName, lastName, speciality, minimalSalary, department));
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

    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor){
        doctor.setId(null);
        try{
            NotNullParameterInRequestValidator.validate(doctor);
            Doctor created = service.addDoctor(doctor);
            return ResponseEntity.ok().body(created);
        } catch(NotNullParameterAbsentException | ConstraintViolationException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
    }

    @PutMapping(name="update",
            path="/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDoctors(@RequestParam(required = true) Long id, @RequestBody Doctor doctor){
        doctor.setId(id);
        try{
            NotNullParameterInRequestValidator.validate(doctor);
            Doctor retPs = service.updateDoctor(doctor);
            return ResponseEntity.ok(retPs);
        } catch (NoSuchElementInDatabaseException | NotNullParameterAbsentException | ConstraintViolationException e){
            return ResponseEntity.status(400).body(Map.of("Reason", e.getMessage()));
        }

    }

    @DeleteMapping(name="delete",
            path="/delete"
    )
    public ResponseEntity<?> deleteDoctor(@RequestParam Long id){
        try{
            service.deleteDoctorById(id);
            return ResponseEntity.ok().build();
        } catch(NoSuchElementInDatabaseException e){
            return ResponseEntity.status(400).body(Map.of("Reason", e.getMessage()));
        }
    }


}
