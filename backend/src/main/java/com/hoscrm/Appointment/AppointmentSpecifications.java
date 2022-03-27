package com.hoscrm.Appointment;

import com.hoscrm.Doctor.Doctor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class AppointmentSpecifications {

    public static Specification<AppointmentToo> hasGreaterCostThan(Double cost){
        return new Specification<AppointmentToo>() {
            @Override
            public Predicate toPredicate(Root<AppointmentToo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (cost == null) ? criteriaBuilder.and() : criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), cost);
            }
        };
    }

    public static Specification<AppointmentToo> hasEqualDoctorFirstName(String doctorFirstName){
        return new Specification<AppointmentToo>() {
            @Override
            public Predicate toPredicate(Root<AppointmentToo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (doctorFirstName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("doctor").get("firstName"), doctorFirstName);
            }
        };
    }

    public static Specification<AppointmentToo> hasEqualDoctorLastName(String doctorLastName){
        return new Specification<AppointmentToo>() {
            @Override
            public Predicate toPredicate(Root<AppointmentToo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (doctorLastName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("doctor").get("lastName"), doctorLastName);
            }
        };
    }

    public static Specification<AppointmentToo> hasEqualPatientFirstName(String patientFirstName){
        return new Specification<AppointmentToo>() {
            @Override
            public Predicate toPredicate(Root<AppointmentToo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (patientFirstName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("patient").get("firstName"), patientFirstName);
            }
        };
    }

    public static Specification<AppointmentToo> hasEqualPatientLastName(String patientLastName){
        return new Specification<AppointmentToo>() {
            @Override
            public Predicate toPredicate(Root<AppointmentToo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (patientLastName == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("patient").get("lastName"), patientLastName);
            }
        };
    }

    public static Specification<AppointmentToo> hasEqualDate(LocalDate date){
        return new Specification<AppointmentToo>() {
            @Override
            public Predicate toPredicate(Root<AppointmentToo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return (date == null) ? criteriaBuilder.and() : criteriaBuilder.equal(root.get("date"), date);
            }
        };
    }



}
