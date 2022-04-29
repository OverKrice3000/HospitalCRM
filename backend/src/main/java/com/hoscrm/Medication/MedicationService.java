package com.hoscrm.Medication;

import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Doctor.DoctorService;
import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Patient.PatientRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MedicationService {

        List<Medication> findMedications(String name, String vendor, Double cost);

        Medication addMedication(Medication info) throws NoSuchElementInDatabaseException;

        Medication updateMedication(Medication Medication);

        boolean deleteMedicationById(Long id);

        boolean deleteMedicationByIdLight(Long id);

}
