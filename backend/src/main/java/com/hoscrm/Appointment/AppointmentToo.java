package com.hoscrm.Appointment;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hoscrm.Deserializers.JsonDateDeserializer;
import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Patient.Patient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class AppointmentToo implements Serializable {

    @EmbeddedId
    private AppointmentIdToo id = new AppointmentIdToo();

    @JsonIgnore
    @ManyToOne(targetEntity = Doctor.class, fetch = FetchType.LAZY)
    @JoinColumn(name="doctor", referencedColumnName = "Id")
    @MapsId("doctorId")
    private Doctor doctor;

    @JsonIgnore
    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY)
    @JoinColumn(name="patient", referencedColumnName = "Id")
    @MapsId("patientId")
    private Patient patient;

    private Date date;
    private Double cost;

    public AppointmentToo(){}

    public AppointmentToo(Doctor doctor, Patient patient, Date date, Double cost) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.cost = cost;
    }

    public AppointmentIdToo getId() {
        return id;
    }

    public void setId(AppointmentIdToo id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDate() {
        return date;
    }

    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setDate(Date date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "doctor=" + doctor +
                ", patient=" + patient +
                ", date=" + date +
                ", cost=" + cost +
                '}';
    }
}
