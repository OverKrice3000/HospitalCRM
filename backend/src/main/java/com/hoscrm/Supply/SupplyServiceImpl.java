package com.hoscrm.Supply;

import com.hoscrm.Department.Department;
import com.hoscrm.Department.DepartmentRepository;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import com.hoscrm.Medication.Medication;
import com.hoscrm.Medication.MedicationRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SupplyServiceImpl implements SupplyService {
    SupplyRepository sRep;
    MedicationRepository mRep;
    DepartmentRepository dRep;
    public SupplyServiceImpl(SupplyRepository sRep, MedicationRepository mRep, DepartmentRepository dRep){
        this.sRep = sRep;
        this.mRep = mRep;
        this.dRep = dRep;
    }

    public List<Supply> findSupplys(String medication, String department, LocalDate date, Double cost){
        return sRep.findAll(
                Specification.where(
                        SupplySpecifications.hasGreaterTotalCostThan(cost).and(
                                SupplySpecifications.hasEqualMedication(medication).and(
                                        SupplySpecifications.hasEqualDepartment(department).and(
                                                SupplySpecifications.hasEqualDate(date)
                                        )
                                )
                        )
                )
        );
    }

    public Supply addSupply(Supply info) throws NoSuchElementInDatabaseException{
        Optional<Medication> medication =  mRep.findById(info.getMedication().getId());
        Optional<Department> department = dRep.findById(info.getDepartment().getId());
        if(medication.isEmpty())
            throw new NoSuchElementInDatabaseException("No entry for medication with id: " + info.getMedication().getId());
        if(department.isEmpty())
            throw new NoSuchElementInDatabaseException("No entry for department with id: " + info.getDepartment().getId());
        double totalCost = info.getSupplySize() * medication.get().getCost();
        info.setDepartment(department.get());
        info.setMedication(medication.get());
        info.getDepartment().setConsumptionDuringMonth(info.getDepartment().getConsumptionDuringMonth() + totalCost);
        info.setTotalCost(totalCost);
        return sRep.save(info);
    }

    public Supply updateSupply(Supply supply){
        Optional<Supply> old = sRep.findById(supply.getId());
        if(old.isEmpty())
            return null;
        supply.setDepartment(old.get().getDepartment());
        supply.setMedication(old.get().getMedication());
        double newTotalCost = old.get().getMedication().getCost() * supply.getSupplySize();
        supply.getDepartment().setConsumptionDuringMonth(supply.getDepartment().getConsumptionDuringMonth() + newTotalCost - old.get().getTotalCost());
        dRep.save(supply.getDepartment());
        supply.setTotalCost(newTotalCost);
        return sRep.save(supply);
    }

    public boolean deleteSupplyById(Long id){
        Optional<Supply> old = sRep.findById(id);
        if(old.isEmpty())
            return false;
        old.get().getDepartment().setConsumptionDuringMonth(old.get().getDepartment().getConsumptionDuringMonth() - old.get().getTotalCost());
        sRep.deleteById(id);
        return true;
    }

    public void updateAllByMedicationId(Long id){
        List<Supply> supplies = sRep.findAllByMedicationId(id);
        for(Supply supply: supplies){
            updateSupply(supply);
        }
    }

    public void deleteAllByMedicationId(Long id){
        List<Supply> supplies = sRep.findAllByMedicationId(id);
        for(Supply supply: supplies){
            deleteSupplyById(supply.getId());
        }
    }

    public boolean deleteSupplyByIdLight(Long id){
        sRep.deleteById(id);
        return true;
    }

}


