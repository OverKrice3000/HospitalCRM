package com.hoscrm.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hoscrm.Deserializers.JsonDateDeserializer;
import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Patient.Patient;
import com.hoscrm.annotations.ReceiveNotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Appointment implements Serializable {

    @ReceiveNotNull(deepValidation = true)
    @EmbeddedId
    private AppointmentId id = new AppointmentId();

    @JsonIgnore
    @ManyToOne(targetEntity = Doctor.class, fetch = FetchType.EAGER)
    @JoinColumn(name="doctor", referencedColumnName = "Id")
    @MapsId("doctorId")
    private Doctor doctor;

    @JsonIgnore
    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.EAGER)
    @JoinColumn(name="patient", referencedColumnName = "Id")
    @MapsId("patientId")
    private Patient patient;

    @ReceiveNotNull
    private Date date;
    @ReceiveNotNull
    private Double cost;

    public Appointment(){}

    public Appointment(Doctor doctor, Patient patient, Date date, Double cost) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.cost = cost;
    }

    public AppointmentId getId() {
        return id;
    }

    public void setId(AppointmentId id) {
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
