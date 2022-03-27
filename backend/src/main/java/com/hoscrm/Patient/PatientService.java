package com.hoscrm.Patient;

import com.hoscrm.Appointment.AppointmentRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    public PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<Patient> findPatients(String firstName, String lastName, Short age){
        return patientRepository.findAll(Example.of(new Patient(firstName, lastName, age)));
    }

    public List<Patient> addPatients(List<Patient> patients){
        return patientRepository.saveAll(patients);
    }

    public Optional<Patient> getPatientById(Long id){
        return patientRepository.findById(id);
    }

    public boolean deletePatientsByIds(List<Long> ids){
        if(!ids.stream().allMatch(i -> {
            return patientRepository.existsById(i);
        }))
            return false;
        patientRepository.deleteAllById(ids);
        return true;
    }

    public List<Patient> updatePatients(List<Patient> patients){
        if(!patients.stream().allMatch(p -> {
            return patientRepository.existsById(p.getId());
        }))
            return null;
        return patientRepository.saveAll(patients);
    }

}
