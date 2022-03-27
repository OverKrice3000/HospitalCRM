package com.hoscrm.Patient;

import com.hoscrm.Appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    public PatientRepository patientRepository;
    private AppointmentRepository aRep; // try to inject in method

    public PatientService(PatientRepository patientRepository, AppointmentRepository aRep){
        this.patientRepository = patientRepository;
        this.aRep = aRep;
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

    public void deletePatientsByIds(List<Long> ids){
        patientRepository.deleteAllById(ids);
    }

}
