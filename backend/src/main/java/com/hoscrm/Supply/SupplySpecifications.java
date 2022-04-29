package com.hoscrm.Supply;

import com.hoscrm.Supply.Supply;
import com.hoscrm.Supply.Supply;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class SupplySpecifications {

    public static Specification<Supply> hasGreaterTotalCostThan(Double totalCost){
        return new Specification<Supply>() {
            @Override
            public Predicate toPredicate(Root<Supply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (totalCost == null) ? criteriaBuilder.and() : criteriaBuilder.greaterThanOrEqualTo(root.get("totalCost"), totalCost);
            }
        };
    }

    public static Specification<Supply> hasEqualMedication(String medication){
        return new Specification<Supply>() {
            @Override
            public Predicate toPredicate(Root<Supply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (medication == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("medication").get("name"), medication);
            }
        };
    }

    public static Specification<Supply> hasEqualDepartment(String department){
        return new Specification<Supply>() {
            @Override
            public Predicate toPredicate(Root<Supply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (department == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("department").get("name"), department);
            }
        };
    }

    public static Specification<Supply> hasEqualDate(LocalDate date){
        return new Specification<Supply>() {
            @Override
            public Predicate toPredicate(Root<Supply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (date == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("date"), date);
            }
        };
    }



}
