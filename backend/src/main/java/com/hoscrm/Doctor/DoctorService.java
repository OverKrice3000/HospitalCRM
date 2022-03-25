package com.hoscrm.Doctor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    DoctorRepository rep;
    public DoctorService(DoctorRepository rep){
        this.rep = rep;
    }

    public List<Doctor> findDoctors(String firstName, String lastName, String speciality, Integer minimalSalary){
        return rep.findAll(
                Specification.where(DoctorSpecifications.hasGreaterSalaryThan(minimalSalary))
                .and(DoctorSpecifications.hasEqualFirstName(firstName)
                .and(DoctorSpecifications.hasEqualLastName(lastName)
                .and(DoctorSpecifications.hasEqualSpeciality(speciality))
                )));
    }
    //SET NULLABLE FALSE AND SEND OBJECT OF NULLS TO DELETE
}
