package com.hoscrm.Appointment;

import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    List<Appointment> findAppointments(String doctorFirstName, String doctorLastName, String patientFirstName,
                                       String patientLastName, LocalDate date, Double cost, String department);
    Appointment addAppointment(Appointment info) throws NoSuchElementInDatabaseException;
    Appointment updateAppointment(Appointment appointment);
    boolean deleteAppointmentById(AppointmentId id);
    boolean deleteAppointmentByIdLight(AppointmentId id);
}
