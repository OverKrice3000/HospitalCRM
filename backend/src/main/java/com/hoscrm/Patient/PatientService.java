package com.hoscrm.Patient;

import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatientService {
    public PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<Patient> findPatients(String firstName, String lastName, Short age, String department){
        return patientRepository.findAll(
                Specification.where(PatientSpecifications.hasEqualFirstName(firstName).and(
                        PatientSpecifications.hasEqualLastName(lastName).and(
                                PatientSpecifications.hasEqualAge(age).and(
                                        PatientSpecifications.hasEqualDepartment(department)
                                )
                        )
                ))
        );
    }

    public Patient addPatient(Patient patient){
        if(patientRepository.existsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName()))
            throw new ConstraintViolationException("Unique constraint violation: Patient with such first name and last name already exists");
        return patientRepository.save(patient);
    }

    public Optional<Patient> getPatientById(Long id){
        return patientRepository.findById(id);
    }

    public void deletePatientById(Long id){
        if(!patientRepository.existsById(id))
            throw new NoSuchElementInDatabaseException("No patient entry with such id: " + id);
        patientRepository.deleteById(id);
    }

    public Patient updatePatient(Patient patient){
        Optional<Patient> found = patientRepository.findById(patient.getId());
        if(found.isEmpty())
            throw new NoSuchElementInDatabaseException("No doctor entry with such id: " + patient.getId());
        Patient tFound = found.get();
        if((!Objects.equals(tFound.getFirstName(), patient.getFirstName()) || !Objects.equals(tFound.getLastName(), patient.getLastName()))
                && patientRepository.existsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName()))
            throw new ConstraintViolationException("Unique constraint violation: Doctor with such first name and last name already exists");
        return patientRepository.save(patient);
    }

}
