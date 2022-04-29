package com.hoscrm.Patient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoscrm.Appointment.Appointment;
import com.hoscrm.annotations.ReceiveNotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name="Patient")
@Table(name="patient", uniqueConstraints = {
        @UniqueConstraint(name="patientNameUniqueConstraint", columnNames = {"firstnamecolumn", "lastnamecolumn"})
})
public class Patient implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientIdSequence")
    @SequenceGenerator(name = "patientIdSequence", sequenceName = "patientidsequence", allocationSize = 1)
    private Long id;
    @ReceiveNotNull
    @Column(
            name = "firstnamecolumn",
          nullable = false
    )
    private String firstName;
    @ReceiveNotNull
    @Column(
            name = "lastnamecolumn",
            nullable = false
    )
    private String lastName;
    @ReceiveNotNull
    @Column(
            name="PatientAgeColumn",
            nullable=false
    )
    private Short age;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointmentSet;
    //TODO: try to delete without set in patient
    //TODO: try to inject aRep in method
    //TODO: SET NULLABLE FALSE AND SEND OBJECT OF NULLS TO DELETE

    public Patient() {
    }

    public Patient(String firstName, String lastName, Short age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

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

    public void setAge(Short age) {
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + ((id == null) ? "null" : id.longValue())  +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + ((age == null) ? "null" : age.shortValue()) +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
