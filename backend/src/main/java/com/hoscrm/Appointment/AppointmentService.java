package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Doctor.DoctorSpecifications;
import com.hoscrm.Patient.Patient;
import com.hoscrm.Patient.PatientRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {
    AppointmentRepository aRep;
    DoctorRepository dRep;
    PatientRepository pRep;
    public AppointmentService(AppointmentRepository aRep, DoctorRepository dRep, PatientRepository pRep){
        this.aRep = aRep;
        this.dRep = dRep;
        this.pRep = pRep;
    }

    public List<AppointmentToo> findAppointments(String doctorFirstName, String doctorLastName, String patientFirstName,
                                                 String patientLastName,LocalDate date, Double cost){
        return aRep.findAll(
                Specification.where(AppointmentSpecifications.hasEqualDate(date).and(
                        AppointmentSpecifications.hasGreaterCostThan(cost).and(
                        AppointmentSpecifications.hasEqualDoctorFirstName(doctorFirstName).and(
                        AppointmentSpecifications.hasEqualDoctorLastName(doctorLastName).and(
                        AppointmentSpecifications.hasEqualPatientFirstName(patientFirstName).and(
                        AppointmentSpecifications.hasEqualPatientLastName(patientLastName)
                        )))))));
    }

    public AppointmentToo addAppointment(AppointmentController.AddAppoinmentRequestInfo info){
        Doctor d = new Doctor(info.doctorFirstName, info.doctorLastName, null, null);
        Patient p = new Patient(info.patientFirstName, info.patientLastName, null);
        d = dRep.findAll(Example.of(d)).get(0);
        p = pRep.findAll(Example.of(p)).get(0);
        return aRep.save(new AppointmentToo(d, p, info.date, info.cost));
    }

    public AppointmentToo updateAppointment(AppointmentToo appointment){
        if(aRep.existsById(appointment.getId()))
            return aRep.save(appointment);
        return null;
    }

    public boolean deleteAppointmentById(AppointmentIdToo id){
        if(!aRep.existsById(id))
            return false;
        aRep.deleteById(id);
        return true;
    }

}
