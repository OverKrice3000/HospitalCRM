package com.hoscrm.Department;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Supply.Supply;
import com.hoscrm.annotations.ReceiveNotNull;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Department {

    @Id
    @SequenceGenerator(name = "departmentSequence", sequenceName = "department_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmentSequence")
    private Long id;

    @ReceiveNotNull
    @Column(name = "departmentName", nullable = false, unique = true)
    private String name;

    @Column(name = "departmentPatients", nullable = false)
    private Integer numberOfPatientsDuringMonth;

    @Column(name = "departmentIncome", nullable = false)
    private Double incomeDuringMonth;

    @Column(name = "departmentCost", nullable = false)
    private Double consumptionDuringMonth;

    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Doctor> doctorSet;

    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Supply> supplySet;

    public Department() {

    }

    public Set<Doctor> getDoctorSet() {
        return doctorSet;
    }

    public void setDoctorSet(Set<Doctor> doctorSet) {
        this.doctorSet = doctorSet;
    }

    public Set<Supply> getSupplySet() {
        return supplySet;
    }

    public void setSupplySet(Set<Supply> supplySet) {
        this.supplySet = supplySet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPatientsDuringMonth() {
        return numberOfPatientsDuringMonth;
    }

    public void setNumberOfPatientsDuringMonth(Integer numberOfPatientsDuringMonth) {
        this.numberOfPatientsDuringMonth = numberOfPatientsDuringMonth;
    }

    public Double getIncomeDuringMonth() {
        return incomeDuringMonth;
    }

    public void setIncomeDuringMonth(Double incomeDuringMonth) {
        this.incomeDuringMonth = incomeDuringMonth;
    }

    public Double getConsumptionDuringMonth() {
        return consumptionDuringMonth;
    }

    public void setConsumptionDuringMonth(Double consumptionDuringMonth) {
        this.consumptionDuringMonth = consumptionDuringMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(numberOfPatientsDuringMonth, that.numberOfPatientsDuringMonth) && Objects.equals(incomeDuringMonth, that.incomeDuringMonth) && Objects.equals(consumptionDuringMonth, that.consumptionDuringMonth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfPatientsDuringMonth, incomeDuringMonth, consumptionDuringMonth);
    }

    public Department(String name) {
        this.name = name;
        this.incomeDuringMonth = 0.;
        this.consumptionDuringMonth = 0.;
        this.numberOfPatientsDuringMonth = 0;
    }
}
