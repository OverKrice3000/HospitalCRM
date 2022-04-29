package com.hoscrm.Medication;

import com.hoscrm.Medication.Medication;
import com.hoscrm.Medication.MedicationService;
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
@RequestMapping(path="api/medication")
public class MedicationController {
    MedicationService service;

    @Resource
    private HttpServletRequest request;

    @Autowired
    public MedicationController(MedicationService service){
        this.service = service;
    }

    @GetMapping(name="find",
            path="/find",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findMedication(@RequestParam(name="name", required = false) String name,
                                             @RequestParam(name="vendor", required = false) String vendor,
                                             @RequestParam(name="cost", required = false) Double minimumCost){
        try{
            request.getParameterMap().forEach((s, ss) -> {
                if(!List.of("name",
                        "vendor",
                        "cost").contains(s))
                    throw new UnexpectedUrlParameterException("Unexpected url parameter: " + s);
            });
        } catch (UnexpectedUrlParameterException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
        return ResponseEntity.ok(service.findMedications(name, vendor, minimumCost));
    }

    @PostMapping(name="add",
            path="/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> addMedication(@RequestBody Medication info){
        try{
            NotNullParameterInRequestValidator.validate(info);
            Medication created = service.addMedication(info);
            return (created == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Medication with such primary key already exists")) :
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
    public ResponseEntity<?> updateMedication(@RequestBody Medication Medication){
        Medication retPs = service.updateMedication(Medication);
        return (retPs == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Medication does not exist in database")) :
                ResponseEntity.ok(retPs);
    }

    @DeleteMapping(name="delete",
            path="/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deletePatient(@RequestBody Long id){
        boolean deleted = service.deleteMedicationById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.status(400).body(Map.of("Reason", "Medication does not exist!"));
    }


}

