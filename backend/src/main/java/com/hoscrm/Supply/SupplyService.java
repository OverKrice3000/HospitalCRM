package com.hoscrm.Supply;

import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;

import java.time.LocalDate;
import java.util.List;

public interface SupplyService {

        List<Supply> findSupplys(String medication, String department, LocalDate date, Double cost);

        Supply addSupply(Supply info) throws NoSuchElementInDatabaseException;

        Supply updateSupply(Supply Supply);

        boolean deleteSupplyById(Long id);

        boolean deleteSupplyByIdLight(Long id);

        void updateAllByMedicationId(Long id);

        void deleteAllByMedicationId(Long id);

}
