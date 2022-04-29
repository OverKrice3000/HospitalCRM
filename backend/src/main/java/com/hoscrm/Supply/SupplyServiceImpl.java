package com.hoscrm.Supply;

import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {
    SupplyRepository sRep;
    public SupplyServiceImpl(SupplyRepository sRep){
        this.sRep = sRep;
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
        double totalCost = info.getSupplySize() * info.getMedication().getCost();
        info.getDepartment().setConsumptionDuringMonth(info.getDepartment().getConsumptionDuringMonth() + totalCost);
        info.setTotalCost(totalCost);
        return sRep.save(info);
    }

    public Supply updateSupply(Supply Supply){
        if(sRep.existsById(Supply.getId()))
            return sRep.save(Supply);
        return null;
    }

    public boolean deleteSupplyById(Long id){
        if(!sRep.existsById(id))
            return false;
        sRep.deleteById(id);
        return true;
    }

    public boolean deleteSupplyByIdLight(Long id){
        sRep.deleteById(id);
        return true;
    }

}


