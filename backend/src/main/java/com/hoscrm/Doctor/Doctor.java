package com.hoscrm.Doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoscrm.Appointment.AppointmentToo;
import com.hoscrm.annotations.ReceiveNotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Doctor implements Serializable {

    @Id
    @SequenceGenerator(name="DoctorIdSequence", sequenceName = "doctoridsequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DoctorIdSequence")
    private Long id;
    @ReceiveNotNull
    @Column(name = "DoctorFirstNameColumn", unique = false, nullable = false, insertable = true, updatable = true, length=64)
    private String firstName;
    @ReceiveNotNull
    @Column(name = "DoctorLastNameColumn", unique = false, nullable = false, insertable = true, updatable = true, length=64)
    private String lastName;
    @ReceiveNotNull
    @Column(name = "DoctorSpecialityColumn", unique = false, nullable = false, insertable = true, updatable = true, length=64)
    private String speciality;
    private Double salary;
    private Integer numberOfPatientsDuringCurrentMonth;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<AppointmentToo> appointmentTooSet;

    public Set<AppointmentToo> getAppointmentTooSet() {
        return appointmentTooSet;
    }

    public void setAppointmentTooSet(Set<AppointmentToo> appointmentTooSet) {
        this.appointmentTooSet = appointmentTooSet;
    }

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
