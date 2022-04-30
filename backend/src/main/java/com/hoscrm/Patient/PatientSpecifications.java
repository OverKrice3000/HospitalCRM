package com.hoscrm.Patient;

import com.hoscrm.Department.Department;
import com.hoscrm.Patient.Patient;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PatientSpecifications {


    public static Specification<Patient> hasEqualFirstName(String firstName){
        return new Specification<Patient>() {
            @Override
            public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (firstName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("firstName"), firstName);
            }
        };
    }

    public static Specification<Patient> hasEqualLastName(String lastName){
        return new Specification<Patient>() {
            @Override
            public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (lastName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("lastName"), lastName);
            }
        };
    }

    public static Specification<Patient> hasEqualAge(Short age){
        return new Specification<Patient>() {
            @Override
            public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (age == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("age"), age);
            }
        };
    }

    public static Specification<Patient> hasEqualDepartment(String department){
        return new Specification<Patient>() {
            @Override
            public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (department == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.join("appointmentSet").get("doctor").get("department").get("name"), department);
            }
        };
    }

}