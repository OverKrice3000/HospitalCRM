package com.hoscrm.Department;

import com.hoscrm.Department.DepartmentSpecifications;
import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository dRep;
    public DepartmentServiceImpl(DepartmentRepository dRep){
        this.dRep = dRep;
    }

    public List<Department> findDepartments(String name, Integer patients, Double income, Double cost){
        return dRep.findAll(
                Specification.where(
                        DepartmentSpecifications.hasGreaterCostThan(cost).and(
                                DepartmentSpecifications.hasEqualName(name).and(
                                        DepartmentSpecifications.hasGreaterPatientsThan(patients).and(
                                                DepartmentSpecifications.hasGreaterIncomeThan(income)
                                        )
                                )
                        )
                )
        );
    }

    public Department addDepartment(Department info) throws NoSuchElementInDatabaseException{
        if(dRep.existsByName(info.getName()))
            throw new ConstraintViolationException("Unique constraint violation: Department with such name already exists");
        info.setConsumptionDuringMonth(0.);
        info.setNumberOfPatientsDuringMonth(0);
        info.setIncomeDuringMonth(0.);
        return dRep.save(info);
    }

    public Department updateDepartment(Department department){
        Optional<Department> old = dRep.findById(department.getId());
        if(old.isEmpty())
            return null;
        department.setIncomeDuringMonth(old.get().getIncomeDuringMonth());
        department.setNumberOfPatientsDuringMonth(old.get().getNumberOfPatientsDuringMonth());
        department.setConsumptionDuringMonth(old.get().getConsumptionDuringMonth());
        return dRep.save(department);
    }

    public boolean deleteDepartmentById(Long id){
        if(!dRep.existsById(id))
            return false;
        dRep.deleteById(id);
        return true;
    }

    public boolean deleteDepartmentByIdLight(Long id){
        dRep.deleteById(id);
        return true;
    }

}


