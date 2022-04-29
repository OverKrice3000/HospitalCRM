package com.hoscrm.Medication;

import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Doctor.DoctorService;
import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Patient.PatientRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {
    MedicationRepository mRep;
    public MedicationServiceImpl(MedicationRepository mRep){
        this.mRep = mRep;
    }

    public List<Medication> findMedications(String name, String vendor, Double cost){
        return mRep.findAll(
                Specification.where(
                        MedicationSpecifications.hasGreaterCostThan(cost).and(
                                MedicationSpecifications.hasEqualName(name).and(
                                        MedicationSpecifications.hasEqualVendor(vendor)
                                )
                        )
                )
        );
    }

    public Medication addMedication(Medication info) throws NoSuchElementInDatabaseException{
        if(mRep.existsByName(info.getName()))
            throw new ConstraintViolationException("Unique constraint violation: Medication with such name already exists");
        return mRep.save(info);
    }

    public Medication updateMedication(Medication Medication){
        if(mRep.existsById(Medication.getId()))
            return mRep.save(Medication);
        return null;
    }

    public boolean deleteMedicationById(Long id){
        if(!mRep.existsById(id))
            return false;
        mRep.deleteById(id);
        return true;
    }

    public boolean deleteMedicationByIdLight(Long id){
        mRep.deleteById(id);
        return true;
    }

}

