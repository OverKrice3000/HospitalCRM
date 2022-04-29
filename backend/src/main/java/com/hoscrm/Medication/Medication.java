package com.hoscrm.Medication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Supply.Supply;
import com.hoscrm.annotations.ReceiveNotNull;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Medication {

    @Id
    @SequenceGenerator(name="MedicationSequence", sequenceName = "medication_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MedicationSequence")
    private Long id;

    @ReceiveNotNull
    @Column(name="MedicationName", unique = true, nullable = false)
    private String name;

    @ReceiveNotNull
    @Column(name="MedicationVendor",  nullable = false)
    private String vendor;

    @ReceiveNotNull
    @Column(name="MedicationCost",  nullable = false)
    private Double cost;

    @JsonIgnore
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Supply> supplySet;

    public Medication(String name, String vendor, Double cost) {
        this.name = name;
        this.vendor = vendor;
        this.cost = cost;
    }

    public Set<Supply> getSupplySet() {
        return supplySet;
    }

    public void setSupplySet(Set<Supply> supplySet) {
        this.supplySet = supplySet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(vendor, that.vendor) && Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vendor, cost);
    }

    public Medication() {

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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
