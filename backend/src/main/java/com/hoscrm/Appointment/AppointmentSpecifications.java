package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class AppointmentSpecifications {

    public static Specification<Appointment> hasGreaterCostThan(Double cost){
        return new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (cost == null) ? criteriaBuilder.and() : criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), cost);
            }
        };
    }

    public static Specification<Appointment> hasEqualDoctorFirstName(String doctorFirstName){
        return new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (doctorFirstName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("doctor").get("firstName"), doctorFirstName);
            }
        };
    }

    public static Specification<Appointment> hasEqualDoctorLastName(String doctorLastName){
        return new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (doctorLastName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("doctor").get("lastName"), doctorLastName);
            }
        };
    }

    public static Specification<Appointment> hasEqualPatientFirstName(String patientFirstName){
        return new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (patientFirstName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("patient").get("firstName"), patientFirstName);
            }
        };
    }

    public static Specification<Appointment> hasEqualPatientLastName(String patientLastName){
        return new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (patientLastName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("patient").get("lastName"), patientLastName);
            }
        };
    }

    public static Specification<Appointment> hasEqualDate(LocalDate date){
        return new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (date == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("date"), date);
            }
        };
    }

    public static Specification<Appointment> hasEqualDepartment(String department){
        return new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (department == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("doctor").get("department").get("name"), department);
            }
        };
    }



}
