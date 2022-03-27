package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Patient.Patient;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@IdClass(AppointmentId.class)
public class Appointment implements Serializable {
    @Id
    @ManyToOne(targetEntity = Doctor.class, fetch = FetchType.LAZY)
    @JoinColumn(name="doctorId", referencedColumnName = "Id")
    private Doctor doctorId;

    @Id
    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY)
    @JoinColumn(name="patientId", referencedColumnName = "Id")
    private Patient patientId;

    private LocalDate date;
    private Double cost;

    public Appointment(){}

    public Appointment(Doctor doctor, Patient patient, LocalDate date, Double cost) {
        this.doctorId = doctor;
        this.patientId = patient;
        this.date = date;
        this.cost = cost;
    }

    public Doctor getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Doctor doctorId) {
        this.doctorId = doctorId;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
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
                "doctor=" + doctorId +
                ", patient=" + patientId +
                ", date=" + date +
                ", cost=" + cost +
                '}';
    }
}
