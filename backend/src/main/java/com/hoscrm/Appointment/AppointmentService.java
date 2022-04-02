package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Doctor.DoctorSpecifications;
import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Patient.Patient;
import com.hoscrm.Patient.PatientRepository;
import org.postgresql.util.PSQLException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AppointmentService{
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

    public AppointmentToo addAppointment(AppointmentToo info) throws NoSuchElementInDatabaseException{
        Optional<Doctor> d = dRep.findById(info.getId().getDoctorId());
        Optional<Patient> p = pRep.findById(info.getId().getPatientId());
        if(d.isEmpty())
            throw new NoSuchElementInDatabaseException("No entry for doctor with id: " + info.getId().getDoctorId());
        if(p.isEmpty())
            throw new NoSuchElementInDatabaseException("No entry for patient with id: " + info.getId().getPatientId());
        if(aRep.existsById(new AppointmentIdToo(d.get().getId(), p.get().getId())))
            throw new ConstraintViolationException("Primary key constraint violation! An appointment with given primary" +
                    " key already exists: {" + info.getId().getDoctorId() + ", " + info.getId().getPatientId() + "}");
        return aRep.save(new AppointmentToo(d.get(), p.get(), info.getDate(), info.getCost()));
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
