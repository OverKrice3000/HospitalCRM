package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentToo, AppointmentIdToo> {

}
