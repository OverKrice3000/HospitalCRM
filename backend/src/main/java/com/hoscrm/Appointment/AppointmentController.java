package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Exceptions.NotNullParameterAbsentException;
import com.hoscrm.Exceptions.UnexpectedUrlParameterException;
import com.hoscrm.Patient.Patient;
import com.hoscrm.Patient.PatientController;
import com.hoscrm.annotations.ReceiveNotNull;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
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

    public ResponseEntity<?> addAppointment(@RequestBody AppointmentToo info){
        try{
            for(Field f: info.getClass().getDeclaredFields()){
                boolean accessible = f.canAccess(info);
                f.setAccessible(true);
                try {
                    if(f.getAnnotation(ReceiveNotNull.class) != null && f.get(info)== null){
                        f.setAccessible(accessible);
                        throw new NotNullParameterAbsentException("Mandatory parameter is missing:  " + f.getName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            AppointmentToo created = service.addAppointment(info);
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
    public ResponseEntity<?> updateAppointment(@RequestBody AppointmentToo appointment){
        AppointmentToo retPs = service.updateAppointment(appointment);
        return (retPs == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Appointment does not exist in database")) :
                ResponseEntity.ok(retPs);
    }

    @DeleteMapping(name="delete",
            path="/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deletePatient(@RequestBody AppointmentIdToo id){
        boolean deleted = service.deleteAppointmentById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.status(400).body(Map.of("Reason", "Appointment does not exist!"));
    }

    static class AddAppoinmentRequestInfo{
        @ReceiveNotNull
        Long doctorId;
        @ReceiveNotNull
        Long patientId;
        @ReceiveNotNull
        //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date;
        @ReceiveNotNull
        Double cost;

        AddAppoinmentRequestInfo(){}

        public Long getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(Long doctorId) {
            this.doctorId = doctorId;
        }

        public Long getPatientId() {
            return patientId;
        }

        public void setPatientId(Long patientId) {
            this.patientId = patientId;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Double getCost() {
            return cost;
        }

        public void setCost(Double cost) {
            this.cost = cost;
        }

        public AddAppoinmentRequestInfo(Long doctorId, Long patientId, LocalDate date, Double cost) {
            this.doctorId = doctorId;
            this.patientId = patientId;
            this.date = date;
            this.cost = cost;
        }
    }


}
