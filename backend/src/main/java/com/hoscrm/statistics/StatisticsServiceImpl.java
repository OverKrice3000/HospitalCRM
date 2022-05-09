package com.hoscrm.statistics;

import com.hoscrm.Department.Department;
import com.hoscrm.Department.DepartmentRepository;
import com.hoscrm.Doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class StatisticsServiceImpl {

    private StatisticsRepository sRep;
    private DoctorRepository dRep;
    private DepartmentRepository ddRep;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository sRep, DoctorRepository dRep, DepartmentRepository ddRep){
        this.sRep = sRep;
        this.dRep = dRep;
        this.ddRep = ddRep;
    }

    public List<Statistics> getStatistics(){
        return sRep.findAllByOrderByDateDesc();
    }

    public List<Statistics> collectStatistics(){
        Optional<Statistics> lastStats = sRep.findFirstByOrderByDateDesc();
        LocalDate newDate = LocalDate.now();
        if(lastStats.isPresent())
            newDate = lastStats.get().getDate().plusMonths(1);
        Statistics newStats = new Statistics();
        newStats.setDate(newDate);
        newStats.setTopIncomeDepartment(ddRep.findFirstByOrderByIncomeDuringMonthDesc());
        newStats.setTopCostDepartment(ddRep.findFirstByOrderByConsumptionDuringMonthDesc());
        newStats.setTopBusyDepartment(ddRep.findFirstByOrderByNumberOfPatientsDuringMonthDesc());
        newStats.setTopBusyDoctor(dRep.findFirstByOrderByNumberOfPatientsDuringCurrentMonthDesc());
        newStats.setTotalIncome(ddRep.sumAllIncomes());
        newStats.setTotalCost(ddRep.sumAllCosts() + dRep.sumAllSalaries());
        ddRep.nullifyStatistics();
        sRep.save(newStats);
        return sRep.findAllByOrderByDateDesc();
    }

}
