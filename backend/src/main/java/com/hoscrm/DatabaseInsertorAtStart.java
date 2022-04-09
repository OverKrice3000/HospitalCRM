package com.hoscrm;

import com.hoscrm.Appointment.Appointment;
import com.hoscrm.Appointment.AppointmentRepository;
import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Patient.Patient;
import com.hoscrm.Patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class DatabaseInsertorAtStart {

    @Autowired
    public DatabaseInsertorAtStart(DoctorRepository dRep, PatientRepository pRep, AppointmentRepository aRep){
        Patient anna = new Patient("Anna", "Karlova", (short) 32);
        Patient petr = new Patient("Petr", "Orlov", (short) 14);
        Patient nataly = new Patient("Nataly", "Amandus", (short) 66);
        pRep.saveAll(List.of(anna, petr, nataly));

        Doctor grigory = new Doctor("Grigory", "London", "Surgeon", 120000.);
        Doctor kollega = new Doctor("Kollega", "Kollega", "Devops", Double.MAX_VALUE);
        Doctor lesha = new Doctor("Lesha", "Ivanov", "Therapist", 40000.);
        dRep.saveAll(List.of(grigory, kollega, lesha));

        Appointment aFirst = new Appointment(grigory, anna, Date.valueOf("2027-5-28"), 2400.);
        Appointment aSecond = new Appointment(kollega, petr, Date.valueOf("2097-1-1"), 19000.);
        Appointment aThird = new Appointment(lesha, nataly, Date.valueOf("2022-3-30"), 6900.);
        Appointment aTrial = new Appointment(kollega, anna, Date.valueOf("2027-5-29"), 3600.);
        aRep.saveAll(List.of(aFirst, aSecond, aThird));
    }
}
