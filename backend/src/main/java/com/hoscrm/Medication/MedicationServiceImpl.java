package com.hoscrm.Medication;

import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Doctor.DoctorService;
import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Patient.PatientRepository;
import com.hoscrm.Supply.SupplyService;
import com.hoscrm.Supply.SupplyServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {
    MedicationRepository mRep;
    SupplyService sServ;
    public MedicationServiceImpl(MedicationRepository mRep, SupplyServiceImpl sServ){
        this.mRep = mRep;
        this.sServ = sServ;
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
        if(mRep.existsByNameAndVendor(info.getName(), info.getVendor()))
            throw new ConstraintViolationException("Unique constraint violation: Medication with such name and vendor already exists");
        return mRep.save(info);
    }

    public Medication updateMedication(Medication medication){
        if(mRep.existsById(medication.getId())){
            Medication ret = mRep.save(medication);
            sServ.updateAllByMedicationId(medication.getId());
            return ret;
        }
        return null;
    }

    public boolean deleteMedicationById(Long id){
        if(!mRep.existsById(id))
            return false;
        sServ.deleteAllByMedicationId(id);
        mRep.deleteById(id);
        return true;
    }

    public boolean deleteMedicationByIdLight(Long id){
        mRep.deleteById(id);
        return true;
    }

}

