package com.hoscrm.Doctor;

import com.hoscrm.Patient.Patient;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Doctor> addDoctors(List<Doctor> doctors){
        return rep.saveAll(doctors);
    }

    public Optional<Doctor> getDoctorById(Long id){
        return rep.findById(id);
    }

    public boolean deleteDoctorsByIds(List<Long> ids){
        if(!ids.stream().allMatch(i -> {
            return rep.existsById(i);
        }))
            return false;
        rep.deleteAllById(ids);
        return true;
    }

    public List<Doctor> updateDoctors(List<Doctor> doctors){
        if(!doctors.stream().allMatch(p -> {
            return rep.existsById(p.getId());
        }))
            return null;
        return rep.saveAll(doctors);
    }

}
