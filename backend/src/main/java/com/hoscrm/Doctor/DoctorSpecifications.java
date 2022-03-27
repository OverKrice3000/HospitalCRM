package com.hoscrm.Doctor;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DoctorSpecifications {

    public static Specification<Doctor> hasGreaterSalaryThan(Integer salary){
        return new Specification<Doctor>() {
            @Override
            public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (salary == null) ? criteriaBuilder.and() : criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), salary);
            }
        };
    }

    public static Specification<Doctor> hasEqualFirstName(String firstName){
        return new Specification<Doctor>() {
            @Override
            public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (firstName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("firstName"), firstName);
            }
        };
    }

    public static Specification<Doctor> hasEqualLastName(String lastName){
        return new Specification<Doctor>() {
            @Override
            public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (lastName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("lastName"), lastName);
            }
        };
    }

    public static Specification<Doctor> hasEqualSpeciality(String speciality){
        return new Specification<Doctor>() {
            @Override
            public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (speciality == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("speciality"), speciality);
            }
        };
    }
}
