package com.hoscrm.Supply;

import com.hoscrm.Supply.Supply;
import com.hoscrm.Supply.SupplyService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="api/supply")
public class SupplyController {
    SupplyService service;

    @Resource
    private HttpServletRequest request;

    @Autowired
    public SupplyController(SupplyService service){
        this.service = service;
    }

    @GetMapping(name="find",
            path="/find",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findSupply(@RequestParam(name="medicament", required = false) String medicament,
                                            @RequestParam(name="department", required = false) String department,
                                            @RequestParam(name="date", required = false) LocalDate date,
                                            @RequestParam(name="cost", required = false) Double cost){
        try{
            request.getParameterMap().forEach((s, ss) -> {
                if(!List.of("medicament",
                        "department",
                        "date",
                        "cost").contains(s))
                    throw new UnexpectedUrlParameterException("Unexpected url parameter: " + s);
            });
        } catch (UnexpectedUrlParameterException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
        return ResponseEntity.ok(service.findSupplys(medicament, department, date, cost));
    }

    @PostMapping(name="add",
            path="/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> addSupply(@RequestBody Supply info){
        try{
            NotNullParameterInRequestValidator.validate(info);
            Supply created = service.addSupply(info);
            return (created == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Supply with such primary key already exists")) :
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
    public ResponseEntity<?> updateSupply(@RequestBody Supply Supply){
        Supply retPs = service.updateSupply(Supply);
        return (retPs == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Supply does not exist in database")) :
                ResponseEntity.ok(retPs);
    }

    @DeleteMapping(name="delete",
            path="/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deletePatient(@RequestBody Long id){
        boolean deleted = service.deleteSupplyById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.status(400).body(Map.of("Reason", "Supply does not exist!"));
    }


}


