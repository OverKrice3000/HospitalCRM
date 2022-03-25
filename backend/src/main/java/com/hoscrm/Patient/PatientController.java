package com.hoscrm.Patient;

import com.hoscrm.Exceptions.UnexpectedUrlParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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


}
