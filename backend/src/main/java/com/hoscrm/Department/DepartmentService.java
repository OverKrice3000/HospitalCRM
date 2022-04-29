package com.hoscrm.Department;

import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DepartmentService {

        public List<Department> findDepartments(String name, Integer patients, Double income, Double cost);

        public Department addDepartment(Department info) throws NoSuchElementInDatabaseException;

        public Department updateDepartment(Department Department);

        public boolean deleteDepartmentById(Long id);

        public boolean deleteDepartmentByIdLight(Long id);

}
