package com.hoscrm;

import com.hoscrm.Appointment.Appointment;
import com.hoscrm.Appointment.AppointmentRepository;
import com.hoscrm.Appointment.AppointmentToo;
import com.hoscrm.Doctor.Doctor;
import com.hoscrm.Doctor.DoctorRepository;
import com.hoscrm.Patient.Patient;
import com.hoscrm.Patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

        AppointmentToo aFirst = new AppointmentToo(grigory, anna, LocalDate.now().plusDays(5), 2400.);
        AppointmentToo aSecond = new AppointmentToo(kollega, petr, LocalDate.now().plusDays(15), 19000.);
        AppointmentToo aThird = new AppointmentToo(lesha, nataly, LocalDate.now().plusDays(8), 6900.);
        aRep.saveAll(List.of(aFirst, aSecond, aThird));
    }
}
