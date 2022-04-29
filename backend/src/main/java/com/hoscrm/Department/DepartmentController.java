package com.hoscrm.Department;

import com.hoscrm.Department.Department;
import com.hoscrm.Department.DepartmentService;
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
@RequestMapping(path="api/department")
public class DepartmentController {
    DepartmentService service;

    @Resource
    private HttpServletRequest request;

    @Autowired
    public DepartmentController(DepartmentService service){
        this.service = service;
    }

    @GetMapping(name="find",
            path="/find",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findDepartment(@RequestParam(name="patients", required = false) Integer minimumPatients,
                                            @RequestParam(name="costs", required = false) Double minimumCost,
                                            @RequestParam(name="incomes", required = false) Double minimumIncome,
                                            @RequestParam(name="name", required = false) String name){
        try{
            request.getParameterMap().forEach((s, ss) -> {
                if(!List.of("name",
                        "patients",
                        "costs",
                        "incomes").contains(s))
                    throw new UnexpectedUrlParameterException("Unexpected url parameter: " + s);
            });
        } catch (UnexpectedUrlParameterException e){
            return ResponseEntity.status(400).body(Map.of("reason", e.getMessage()));
        }
        return ResponseEntity.ok(service.findDepartments(name, minimumPatients, minimumIncome, minimumCost));
    }

    @PostMapping(name="add",
            path="/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> addDepartment(@RequestBody Department info){
        try{
            NotNullParameterInRequestValidator.validate(info);
            Department created = service.addDepartment(info);
            return (created == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Department with such primary key already exists")) :
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
    public ResponseEntity<?> updateDepartment(@RequestParam(required = true) Long id, @RequestBody Department department){
        department.setId(id);
        Department retPs = service.updateDepartment(department);
        return (retPs == null) ? ResponseEntity.status(400).body(Map.of("Reason", "Department does not exist in database")) :
                ResponseEntity.ok(retPs);
    }

    @DeleteMapping(name="delete",
            path="/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteDepartment(@RequestParam Long id){
        boolean deleted = service.deleteDepartmentById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.status(400).body(Map.of("Reason", "Department does not exist!"));
    }


}

