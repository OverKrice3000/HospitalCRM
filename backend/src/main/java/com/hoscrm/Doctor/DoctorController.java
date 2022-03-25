package com.hoscrm.Doctor;

import com.hoscrm.Exceptions.UnexpectedUrlParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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


}
