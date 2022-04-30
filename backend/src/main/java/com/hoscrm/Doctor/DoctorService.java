package com.hoscrm.Doctor;

import com.hoscrm.Exceptions.ConstraintViolationException;
import com.hoscrm.Exceptions.NoSuchElementInDatabaseException;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    List<Doctor> findDoctors(String firstName, String lastName, String speciality, Integer minimalSalary, String department);

    Doctor addDoctor(Doctor doctor) throws ConstraintViolationException;

    Optional<Doctor> getDoctorById(Long id);

    void deleteDoctorById(Long id) throws NoSuchElementInDatabaseException;

    Doctor updateDoctor(Doctor doctor) throws NoSuchElementInDatabaseException, ConstraintViolationException;

}
