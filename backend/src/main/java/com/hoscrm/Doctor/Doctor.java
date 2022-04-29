package com.hoscrm.Doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoscrm.Appointment.Appointment;
import com.hoscrm.Department.Department;
import com.hoscrm.annotations.ReceiveNotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="doctor", uniqueConstraints = {
        @UniqueConstraint(name="doctorNameUniqueConstraint", columnNames = {"DoctorFirstNameColumn", "DoctorLastNameColumn"})
})
public class Doctor implements Serializable {

    @Id
    @SequenceGenerator(name="DoctorIdSequence", sequenceName = "doctoridsequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DoctorIdSequence")
    private Long id;
    @ReceiveNotNull
    @Column(name = "DoctorFirstNameColumn", nullable = false, length=64)
    private String firstName;
    @ReceiveNotNull
    @Column(name = "DoctorLastNameColumn",  nullable = false, length=64)
    private String lastName;
    @ReceiveNotNull
    @Column(name = "DoctorSpecialityColumn",  nullable = false, length=64)
    private String speciality;
    @ReceiveNotNull
    @Column(name = "DoctorSalaryColumn", nullable = false)
    private Double salary;
    private Integer numberOfPatientsDuringCurrentMonth;
    @ReceiveNotNull
    @JoinColumn(referencedColumnName = "id", name="DoctorDepartment", nullable = false)
    @ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER)
    private Department department;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointmentSet;

    public Doctor(String firstName, String lastName, String speciality, Double salary, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.salary = salary;
        this.numberOfPatientsDuringCurrentMonth = 0;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Appointment> getAppointmentSet() {
        return appointmentSet;
    }

    public void setAppointmentSet(Set<Appointment> appointmentSet) {
        this.appointmentSet = appointmentSet;
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
