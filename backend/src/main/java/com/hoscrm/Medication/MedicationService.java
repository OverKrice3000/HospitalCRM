package com.hoscrm.Medication;

import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;

import java.util.List;

public interface MedicationService {

        List<Medication> findMedications(String name, String vendor, Double cost);

        Medication addMedication(Medication info) throws NoSuchElementInDatabaseException;

        Medication updateMedication(Medication Medication);

        boolean deleteMedicationById(Long id);

        boolean deleteMedicationByIdLight(Long id);

}
