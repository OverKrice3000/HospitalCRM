package com.hoscrm.Doctor;

import com.hoscrm.Department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    Doctor findFirstByOrderByNumberOfPatientsDuringCurrentMonthDesc();
    @Query("select sum(e.salary) from Doctor e")
    Double sumAllSalaries();
}
