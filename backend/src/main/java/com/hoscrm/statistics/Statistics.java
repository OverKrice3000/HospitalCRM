package com.hoscrm.statistics;


import com.hoscrm.Department.Department;
import com.hoscrm.Doctor.Doctor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Statistics {

    @Id
    @SequenceGenerator(
            name="statisticsSequence",
            sequenceName = "statistics_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "statisticsSequence"
    )
    private Long id;

    @Column(name="date", nullable = false)
    private LocalDate date;
    @JoinColumn(referencedColumnName = "id", name="topIncomeDepartment", nullable = false)
    @ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER)
    private Department topIncomeDepartment;
    @JoinColumn(referencedColumnName = "id", name="topCostDepartment", nullable = false)
    @ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER)
    private Department topCostDepartment;
    @JoinColumn(referencedColumnName = "id", name="topBusyDoctor", nullable = false)
    @ManyToOne(targetEntity = Doctor.class, fetch = FetchType.EAGER)
    private Doctor topBusyDoctor;
    @JoinColumn(referencedColumnName = "id", name="topBusyDepartment", nullable = false)
    @ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER)
    private Department topBusyDepartment;
    @Column(name="total_income", nullable = false)
    private Double totalIncome;
    @Column(name="total_cost", nullable = false)
    private Double totalCost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(topIncomeDepartment, that.topIncomeDepartment) && Objects.equals(topCostDepartment, that.topCostDepartment) && Objects.equals(topBusyDoctor, that.topBusyDoctor) && Objects.equals(topBusyDepartment, that.topBusyDepartment) && Objects.equals(totalIncome, that.totalIncome) && Objects.equals(totalCost, that.totalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, topIncomeDepartment, topCostDepartment, topBusyDoctor, topBusyDepartment, totalIncome, totalCost);
    }

    public Statistics() {
    }


    public Statistics(Long id, LocalDate date, Department topIncomeDepartment, Department topCostDepartment, Doctor topBusyDoctor, Department topBusyDepartment, Double totalIncome, Double totalCost) {
        this.id = id;
        this.date = date;
        this.topIncomeDepartment = topIncomeDepartment;
        this.topCostDepartment = topCostDepartment;
        this.topBusyDoctor = topBusyDoctor;
        this.topBusyDepartment = topBusyDepartment;
        this.totalIncome = totalIncome;
        this.totalCost = totalCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Department getTopIncomeDepartment() {
        return topIncomeDepartment;
    }

    public void setTopIncomeDepartment(Department topIncomeDepartment) {
        this.topIncomeDepartment = topIncomeDepartment;
    }

    public Department getTopCostDepartment() {
        return topCostDepartment;
    }

    public void setTopCostDepartment(Department topCostDepartment) {
        this.topCostDepartment = topCostDepartment;
    }

    public Doctor getTopBusyDoctor() {
        return topBusyDoctor;
    }

    public void setTopBusyDoctor(Doctor topBusyDoctor) {
        this.topBusyDoctor = topBusyDoctor;
    }

    public Department getTopBusyDepartment() {
        return topBusyDepartment;
    }

    public void setTopBusyDepartment(Department topBusyDepartment) {
        this.topBusyDepartment = topBusyDepartment;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }


}
