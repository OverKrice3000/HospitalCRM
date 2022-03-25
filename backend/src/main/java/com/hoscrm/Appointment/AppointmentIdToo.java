package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Patient.Patient;

import java.io.Serializable;
import java.util.Objects;

public class AppointmentIdToo implements Serializable {
    private Doctor doctor;

    public AppointmentIdToo(Doctor doctor) {
        this.doctor = doctor;
    }

    public AppointmentIdToo(){}

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentIdToo that = (AppointmentIdToo) o;
        return Objects.equals(doctor, that.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctor);
    }
}
