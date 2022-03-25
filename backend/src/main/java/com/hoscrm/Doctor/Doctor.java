package com.hoscrm.Doctor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Doctor implements Serializable {

    @Id
    @SequenceGenerator(name="DoctorIdSequence", sequenceName = "doctoridsequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DoctorIdSequence")
    private Long id;
    @Column(name = "DoctorFirstNameColumn", unique = false, nullable = false, insertable = true, updatable = true, length=64)
    private String firstName;
    @Column(name = "DoctorLastNameColumn", unique = false, nullable = false, insertable = true, updatable = true, length=64)
    private String lastName;
    @Column(name = "DoctorSpecialityColumn", unique = false, nullable = false, insertable = true, updatable = true, length=64)
    private String speciality;
    private Double salary;
    private Integer numberOfPatientsDuringCurrentMonth;

    public Doctor(String firstName, String lastName, String speciality, Double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.salary = salary;
        this.numberOfPatientsDuringCurrentMonth = 0;
    }

    public Doctor(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getNumberOfPatientsDuringCurrentMonth() {
        return numberOfPatientsDuringCurrentMonth;
    }

    public void setNumberOfPatientsDuringCurrentMonth(Integer numberOfPatientsDuringCurrentMonth) {
        this.numberOfPatientsDuringCurrentMonth = numberOfPatientsDuringCurrentMonth;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", speciality='" + speciality + '\'' +
                ", salary=" + salary +
                ", numberOfPatientsDuringCurrentMonth=" + numberOfPatientsDuringCurrentMonth +
                '}';
    }
}
