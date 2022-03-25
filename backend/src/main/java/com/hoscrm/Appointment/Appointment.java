package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Patient.Patient;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
/*@Table(name="appointment", uniqueConstraints= {
        @UniqueConstraint(name = "DoctorAndPatientAppointmentUniqueConstraint", columnNames ={"doctorId", "patientId"})
})*/
public class Appointment implements Serializable {

    @EmbeddedId
    AppointmentId id = new AppointmentId();

    @ManyToOne(targetEntity = Doctor.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="doctor", referencedColumnName = "Id")
    @MapsId("doctorId")
    private Doctor doctor;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="patient", referencedColumnName = "Id")
    @MapsId("patientId")
    private Patient patient;

    private LocalDate date;
    private Double cost;

    public Appointment(){}

    public Appointment(Doctor doctor, Patient patient, LocalDate date, Double cost) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
