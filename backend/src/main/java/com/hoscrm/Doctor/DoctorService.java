package com.hoscrm.Doctor;

import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    DoctorRepository rep;
    public DoctorService(DoctorRepository rep){
        this.rep = rep;
    }

    public List<Doctor> findDoctors(String firstName, String lastName, String speciality, Integer minimalSalary){
        return rep.findAll(
                Specification.where(DoctorSpecifications.hasGreaterSalaryThan(minimalSalary))
                .and(DoctorSpecifications.hasEqualFirstName(firstName)
                .and(DoctorSpecifications.hasEqualLastName(lastName)
                .and(DoctorSpecifications.hasEqualSpeciality(speciality))
                )));
    }

    public Doctor addDoctor(Doctor doctor) throws ConstraintViolationException{
        doctor.setNumberOfPatientsDuringCurrentMonth(0);
        if(rep.existsByFirstNameAndLastName(doctor.getFirstName(), doctor.getLastName()))
            throw new ConstraintViolationException("Unique constraint violation: Doctor with such first name and last name already exists");
        return rep.save(doctor);
    }

    public Optional<Doctor> getDoctorById(Long id){
        return rep.findById(id);
    }

    public void deleteDoctorById(Long id) throws NoSuchElementInDatabaseException{
        if(!rep.existsById(id))
            throw new NoSuchElementInDatabaseException("No doctor entry with such id: " + id);
        rep.deleteById(id);

    }

    public Doctor updateDoctor(Doctor doctor) throws NoSuchElementInDatabaseException, ConstraintViolationException{
        Optional<Doctor> found = rep.findById(doctor.getId());
        if(found.isEmpty())
            throw new NoSuchElementInDatabaseException("No doctor entry with such id: " + doctor.getId());
        doctor.setNumberOfPatientsDuringCurrentMonth(found.get().getNumberOfPatientsDuringCurrentMonth());
        return rep.save(doctor);
    }

}
