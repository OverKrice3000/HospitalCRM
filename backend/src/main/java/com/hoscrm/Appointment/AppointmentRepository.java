package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentToo, AppointmentIdToo>, JpaSpecificationExecutor<AppointmentToo> {

}
