package com.hoscrm.Medication;

import com.hoscrm.Medication.Medication;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class MedicationSpecifications {

    public static Specification<Medication> hasGreaterCostThan(Double cost){
        return new Specification<Medication>() {
            @Override
            public Predicate toPredicate(Root<Medication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (cost == null) ? criteriaBuilder.and() : criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), cost);
            }
        };
    }

    public static Specification<Medication> hasEqualName(String name){
        return new Specification<Medication>() {
            @Override
            public Predicate toPredicate(Root<Medication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (name == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("name"), name);
            }
        };
    }

    public static Specification<Medication> hasEqualVendor(String vendor){
        return new Specification<Medication>() {
            @Override
            public Predicate toPredicate(Root<Medication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (vendor == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("vendor"), vendor);
            }
        };
    }



}
