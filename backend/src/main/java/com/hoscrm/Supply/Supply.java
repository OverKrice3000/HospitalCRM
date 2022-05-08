package com.hoscrm.Supply;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hoscrm.Department.Department;
import com.hoscrm.Deserializers.JsonDateDeserializer;
import com.hoscrm.Medication.Medication;
import com.hoscrm.annotations.ReceiveNotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
public class Supply {

    @Id
    @SequenceGenerator(name="SupplySequence", sequenceName = "supply_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SupplySequence")
    private Long id;

    @Column(name="supplySize", nullable = false)
    @ReceiveNotNull
    private Integer supplySize;

    @Column(name="supplyTotalCost", nullable=false)
    private Double totalCost;

    @Column(name="supplyDate", nullable = false)
    @ReceiveNotNull
    private LocalDate date;

    @ManyToOne(targetEntity = Medication.class, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", name = "supplyMedication", nullable = false)
    @ReceiveNotNull
    private Medication medication;

    @ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", name = "supplyDepartment", nullable = false)
    @ReceiveNotNull
    private Department department;

    public Supply() {

    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSupplySize() {
        return supplySize;
    }

    public void setSupplySize(Integer supplySize) {
        this.supplySize = supplySize;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Supply(Integer supplySize, Medication medication, Department department, LocalDate date) {
        this.supplySize = supplySize;
        this.medication = medication;
        this.date = date;
        this.department = department;
        this.totalCost = medication.getCost() * supplySize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supply supply = (Supply) o;
        return Objects.equals(id, supply.id) && Objects.equals(supplySize, supply.supplySize) && Objects.equals(totalCost, supply.totalCost) && Objects.equals(medication, supply.medication) && Objects.equals(department, supply.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplySize, totalCost, medication, department);
    }
}
