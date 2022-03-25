package com.hoscrm.Patient;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    public PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<Patient> findPatients(String firstName, String lastName, Short age){
        return patientRepository.findAll(Example.of(new Patient(firstName, lastName, age)));
    }

}
