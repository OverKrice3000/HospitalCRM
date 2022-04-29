package com.hoscrm;

import com.hoscrm.Appointment.Appointment;
import com.hoscrm.Appointment.AppointmentRepository;
import com.hoscrm.Department.Department;
import com.hoscrm.Department.DepartmentRepository;
import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Medication.Medication;
import com.hoscrm.Medication.MedicationRepository;
import com.hoscrm.Patient.Patient;
import com.hoscrm.Patient.PatientRepository;
import com.hoscrm.Supply.Supply;
import com.hoscrm.Supply.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class DatabaseInsertorAtStart {

    @Autowired
    public DatabaseInsertorAtStart(DoctorRepository dRep, PatientRepository pRep, AppointmentRepository aRep, DepartmentRepository ddRep,
                                   SupplyRepository sRep, MedicationRepository mRep){
        Department d505013 = new Department("d505013");
        Department d192586 = new Department("d192586");
        Department d915386 = new Department("d915386");
        ddRep.saveAll(List.of(d192586, d915386, d505013));

        Medication ibyprofen = new Medication("ibyprofen", "Oracle", 880.);
        Medication aktivirovanniyYgol = new Medication("aktivirovanniyYgol", "Apple", 10.);
        Medication Analgin = new Medication("Analgin", "Microsoft", 2000.);
        mRep.saveAll(List.of(ibyprofen, aktivirovanniyYgol, Analgin));

        Supply s1 = new Supply(500, ibyprofen, d192586, new java.util.Date());
        Supply s2 = new Supply(2000000, aktivirovanniyYgol, d915386,  new java.util.Date());
        Supply s3 = new Supply(1000, Analgin, d505013,  new java.util.Date());
        sRep.saveAll(List.of(s1, s2, s3));

        Patient anna = new Patient("Anna", "Karlova", (short) 32);
        Patient petr = new Patient("Petr", "Orlov", (short) 14);
        Patient nataly = new Patient("Nataly", "Amandus", (short) 66);
        pRep.saveAll(List.of(anna, petr, nataly));

        Doctor grigory = new Doctor("Grigory", "London", "Surgeon", 120000., d192586);
        Doctor kollega = new Doctor("Kollega", "Kollega", "Devops", Double.MAX_VALUE, d505013);
        Doctor lesha = new Doctor("Lesha", "Ivanov", "Therapist", 40000., d915386);
        dRep.saveAll(List.of(grigory, kollega, lesha));

        Appointment aFirst = new Appointment(grigory, anna, Date.valueOf("2027-5-28"), 2400.);
        Appointment aSecond = new Appointment(kollega, petr, Date.valueOf("2097-1-1"), 19000.);
        Appointment aThird = new Appointment(lesha, nataly, Date.valueOf("2022-3-30"), 6900.);
        Appointment aTrial = new Appointment(kollega, anna, Date.valueOf("2027-5-29"), 3600.);
        aRep.saveAll(List.of(aFirst, aSecond, aThird));
    }
}
