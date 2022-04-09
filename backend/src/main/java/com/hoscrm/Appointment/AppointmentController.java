package com.hoscrm.Appointment;

import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Exceptions.NotNullParameterAbsentException;
import com.hoscrm.Exceptions.UnexpectedUrlParameterException;
import com.hoscrm.Validators.NotNullParameterInRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="api/appointment")
public class AppointmentController {
    AppointmentService service;

    @Resource
    private HttpServletRequest request;

    @Autowired
    public AppointmentController(AppointmentService service){
        this.service = service;
    }

    @GetMapping(name="find",
                path="/find",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAppointment(@RequestParam(name="doctorfirstname", required = false) String doctorFirstName,
                                             @RequestParam(name="doctorlastname", required = false) String doctorLastName,
                                             @RequestParam(name="patientfirstname", required = false) String patientFirstName,
                                             @RequestParam(name="patientlastname", required = false) String patientLastName,
                                             @RequestParam(name="date", required = false) LocalDate date,
                                             @RequestParam(name="cost", required = false) Double minimumCost){
        try{
            request.getParameterMap().forEach((s, ss) -> {
                if(!List.of("doctorfirstname",
                        "doctorlastname",
                        "patientfirstname",
                        "patientlastname",
                        "date",
                        "cost").contains(s))
                    throw new UnexpectedUrlParameterException("Unexpected url parameter: " + s);
            });
        } catch (UnexpectedUrlParameterException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
        return ResponseEntity.ok(service.findAppointments(doctorFirstName, doctorLastName, patientFirstName, patientLastName, date, minimumCost));
    }

    @PostMapping(name="add",
            path="/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> addAppointment(@RequestBody Appointment info){
        try{
            NotNullParameterInRequestValidator.validate(info);
            Appointment created = service.addAppointment(info);
            return (created == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Appointment with such primary key already exists")) :
                    ResponseEntity.ok().body(created);
        }
        catch(NotNullParameterAbsentException |
                ConstraintViolationException |
                NoSuchElementInDatabaseException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
    }

    @PutMapping(name="update",
            path="/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAppointment(@RequestBody Appointment appointment){
        Appointment retPs = service.updateAppointment(appointment);
        return (retPs == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Appointment does not exist in database")) :
                ResponseEntity.ok(retPs);
    }

    @DeleteMapping(name="delete",
            path="/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deletePatient(@RequestBody AppointmentId id){
        boolean deleted = service.deleteAppointmentById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.status(400).body(Map.of("Reason", "Appointment does not exist!"));
    }


}
