package com.hoscrm;

import com.hoscrm.Appointment.Appointment;
import com.hoscrm.Appointment.AppointmentId;
import com.hoscrm.Appointment.AppointmentRepository;
import com.hoscrm.Appointment.AppointmentServiceImpl;
import com.hoscrm.Department.Department;
import com.hoscrm.Department.DepartmentRepository;
import com.hoscrm.Department.DepartmentServiceImpl;
import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Doctor.DoctorServiceImpl;
import com.hoscrm.Medication.Medication;
import com.hoscrm.Medication.MedicationRepository;
import com.hoscrm.Medication.MedicationServiceImpl;
import com.hoscrm.Patient.Patient;
import com.hoscrm.Patient.PatientRepository;
import com.hoscrm.Patient.PatientService;
import com.hoscrm.Supply.Supply;
import com.hoscrm.Supply.SupplyRepository;
import com.hoscrm.Supply.SupplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DatabaseInsertorAtStart {

    @Autowired
    public DatabaseInsertorAtStart(DoctorRepository dRep, PatientRepository pRep, AppointmentRepository aRep, DepartmentRepository ddRep,
                                   SupplyRepository sRep, MedicationRepository mRep, DoctorServiceImpl dServ, PatientService pServ,
                                   AppointmentServiceImpl aServ, DepartmentServiceImpl ddServ, SupplyServiceImpl sServ, MedicationServiceImpl mServ){
        Department d505013 = new Department("d505013");
        Department d192586 = new Department("d192586");
        Department d915386 = new Department("d915386");
        ddServ.addDepartment(d192586);
        ddServ.addDepartment(d505013);
        ddServ.addDepartment(d915386);

        Medication ibyprofen = new Medication("ibyprofen", "Oracle", 880.);
        Medication aktivirovanniyYgol = new Medication("aktivirovanniyYgol", "Apple", 10.);
        Medication Analgin = new Medication("Analgin", "Microsoft", 2000.);
        mServ.addMedication(ibyprofen);
        mServ.addMedication(aktivirovanniyYgol);
        mServ.addMedication(Analgin);

        Supply s1 = new Supply(500, ibyprofen, d192586, LocalDate.parse("2027-05-28"));
        Supply s2 = new Supply(2000000, aktivirovanniyYgol, d915386,  LocalDate.parse("2097-01-01"));
        Supply s3 = new Supply(1000, Analgin, d505013,  LocalDate.parse("2022-03-30"));
        sServ.addSupply(s1);
        sServ.addSupply(s2);
        sServ.addSupply(s3);

        Patient anna = new Patient("Anna", "Karlova", (short) 32);
        Patient petr = new Patient("Petr", "Orlov", (short) 14);
        Patient nataly = new Patient("Nataly", "Amandus", (short) 66);
        pServ.addPatient(anna);
        pServ.addPatient(petr);
        pServ.addPatient(nataly);

        Doctor grigory = new Doctor("Grigory", "London", "Surgeon", 120000., d192586);
        Doctor kollega = new Doctor("Kollega", "Kollega", "Devops", 3000, d505013);
        Doctor lesha = new Doctor("Lesha", "Ivanov", "Therapist", 40000., d915386);
        dServ.addDoctor(grigory);
        dServ.addDoctor(kollega);
        dServ.addDoctor(lesha);

        Appointment aFirst = new Appointment(grigory, anna, LocalDate.parse("2027-05-28"), 2400.);
        aFirst.setId(new AppointmentId(grigory.getId(), anna.getId()));
        Appointment aSecond = new Appointment(kollega, petr, LocalDate.parse("2097-01-01"), 19000.);
        aSecond.setId(new AppointmentId(kollega.getId(), petr.getId()));
        Appointment aThird = new Appointment(lesha, nataly, LocalDate.parse("2022-03-30"), 6900.);
        aThird.setId(new AppointmentId(lesha.getId(), nataly.getId()));
        aServ.addAppointment(aFirst);
        aServ.addAppointment(aSecond);
        aServ.addAppointment(aThird);
    }
}
