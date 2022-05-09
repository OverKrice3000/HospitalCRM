package com.hoscrm.Appointment;

import com.hoscrm.Department.DepartmentRepository;
import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Doctor.DoctorServiceImpl;
import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Patient.Patient;
import com.hoscrm.Patient.PatientRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    AppointmentRepository aRep;
    DoctorRepository dRep;
    DepartmentRepository ddRep;
    PatientRepository pRep;
    DoctorServiceImpl dService;
    public AppointmentServiceImpl(AppointmentRepository aRep, DepartmentRepository ddRep, DoctorRepository dRep, PatientRepository pRep, DoctorServiceImpl dService){
        this.aRep = aRep;
        this.dRep = dRep;
        this.ddRep = ddRep;
        this.pRep = pRep;
        this.dService = dService;
    }

    public List<Appointment> findAppointments(String doctorFirstName, String doctorLastName, String patientFirstName,
                                              String patientLastName, LocalDate date, Double cost, String department){
        return aRep.findAll(
                Specification.where(AppointmentSpecifications.hasEqualDate(date).and(
                        AppointmentSpecifications.hasGreaterCostThan(cost).and(
                        AppointmentSpecifications.hasEqualDoctorFirstName(doctorFirstName).and(
                        AppointmentSpecifications.hasEqualDoctorLastName(doctorLastName).and(
                        AppointmentSpecifications.hasEqualPatientFirstName(patientFirstName).and(
                        AppointmentSpecifications.hasEqualPatientLastName(patientLastName).and(
                        AppointmentSpecifications.hasEqualDepartment(department)
                        ))))))));
    }
    public Appointment addAppointment(Appointment info) throws NoSuchElementInDatabaseException{
        Optional<Doctor> d = dRep.findById(info.getId().getDoctorId());
        Optional<Patient> p = pRep.findById(info.getId().getPatientId());
        if(d.isEmpty())
            throw new NoSuchElementInDatabaseException("No entry for doctor with id: " + info.getId().getDoctorId());
        if(p.isEmpty())
            throw new NoSuchElementInDatabaseException("No entry for patient with id: " + info.getId().getPatientId());
        if(aRep.existsById(new AppointmentId(d.get().getId(), p.get().getId())))
            throw new ConstraintViolationException("Primary key constraint violation! An appointment with given primary" +
                    " key already exists: {" + info.getId().getDoctorId() + ", " + info.getId().getPatientId() + "}");
        d.get().setNumberOfPatientsDuringCurrentMonth(d.get().getNumberOfPatientsDuringCurrentMonth() + 1);
        d.get().getDepartment().setNumberOfPatientsDuringMonth(d.get().getDepartment().getNumberOfPatientsDuringMonth() + 1);
        d.get().getDepartment().setIncomeDuringMonth(d.get().getDepartment().getIncomeDuringMonth() + info.getCost());
        dRep.save(d.get());
        ddRep.save(d.get().getDepartment());
        return aRep.save(new Appointment(d.get(), p.get(), info.getDate(), info.getCost()));
    }

    public Appointment updateAppointment(Appointment appointment){
        Optional<Appointment> old = aRep.findById(appointment.getId());
        if(old.isEmpty())
            return null;
        old.get().getDoctor().getDepartment().setConsumptionDuringMonth(old.get().getDoctor().getDepartment().getConsumptionDuringMonth() - old.get().getCost() + appointment.getCost());
        ddRep.save(old.get().getDoctor().getDepartment());
        return aRep.save(appointment);
    }

    public boolean deleteAppointmentById(AppointmentId id){
        Optional<Appointment> old = aRep.findById(id);
        if(old.isEmpty())
            return false;
        old.get().getDoctor().getDepartment().setConsumptionDuringMonth(old.get().getDoctor().getDepartment().getConsumptionDuringMonth() - old.get().getCost());
        ddRep.save(old.get().getDoctor().getDepartment());
        aRep.deleteById(id);
        return true;
    }

    public boolean deleteAppointmentByIdLight(AppointmentId id){
        aRep.deleteById(id);
        return true;
    }

}
