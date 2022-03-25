package com.hoscrm.Appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping(path="api/appointment")
public class AppointmentController {
    AppointmentService service;

    @Autowired
    public AppointmentController(AppointmentService service){
        this.service = service;
    }

    public ResponseEntity<?> findAppointment(@RequestParam(name="date", required = false) LocalDate date,
                                             @RequestParam(name="cost", required = false) Double minimumCost){

        return ResponseEntity.ok().build();
    }
}
