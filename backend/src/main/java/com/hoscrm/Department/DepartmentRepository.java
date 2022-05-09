package com.hoscrm.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    boolean existsByName(String name);
    Department findFirstByOrderByIncomeDuringMonthDesc();
    Department findFirstByOrderByConsumptionDuringMonthDesc();
    Department findFirstByOrderByNumberOfPatientsDuringMonthDesc();
    @Query("select sum(e.incomeDuringMonth) from Department e")
    Double sumAllIncomes();
    @Query("select sum(e.consumptionDuringMonth) from Department e")
    Double sumAllCosts();
    @Modifying
    @Transactional
    @Query("update Department e SET e.incomeDuringMonth = 0, e.consumptionDuringMonth = 0, e.numberOfPatientsDuringMonth = 0")
    void nullifyStatistics();
}
