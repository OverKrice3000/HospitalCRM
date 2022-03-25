package com.hoscrm.Patient;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity(name="Patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientIdSequence")
    @SequenceGenerator(name = "patientIdSequence", sequenceName = "patientidsequence", allocationSize = 1)
    Long id;
    @Column(
            name = "firstnamecolumn",
          nullable = false
    )
    String firstName;
    @Column(
            name = "lastnamecolumn",
            nullable = false
    )
    String lastName;
    @Column(
            name = "agecolumn",
            nullable = false
    )
    Short age;

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

    public void setId(long id) {
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
}
