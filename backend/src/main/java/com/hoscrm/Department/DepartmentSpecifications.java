package com.hoscrm.Department;

import com.hoscrm.Department.Department;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DepartmentSpecifications {

    public static Specification<Department> hasGreaterCostThan(Double cost){
        return new Specification<Department>() {
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (cost == null) ? criteriaBuilder.and() : criteriaBuilder.greaterThanOrEqualTo(root.get("consumptionDuringMonth"), cost);
            }
        };
    }

    public static Specification<Department> hasGreaterIncomeThan(Double income){
        return new Specification<Department>() {
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (income == null) ? criteriaBuilder.and() : criteriaBuilder.greaterThanOrEqualTo(root.get("incomeDuringMonth"), income);
            }
        };
    }

    public static Specification<Department> hasGreaterPatientsThan(Integer patients){
        return new Specification<Department>() {
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (patients == null) ? criteriaBuilder.and() : criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfPatientsDuringMont"), patients);
            }
        };
    }

    public static Specification<Department> hasEqualName(String name){
        return new Specification<Department>() {
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (name == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("name"), name);
            }
        };
    }

}
