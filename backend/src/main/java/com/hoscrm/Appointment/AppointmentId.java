package com.hoscrm.Appointment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

public class AppointmentId implements Serializable {
    public Long doctorId;
    public Long patientId;

    public AppointmentId(){}

    public AppointmentId(Long doctorId, Long patientId) {
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentId that = (AppointmentId) o;
        return Objects.equals(doctorId, that.doctorId) && Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, patientId);
    }
}

