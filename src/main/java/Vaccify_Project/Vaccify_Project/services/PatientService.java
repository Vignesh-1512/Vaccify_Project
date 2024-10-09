package Vaccify_Project.Vaccify_Project.services;

import Vaccify_Project.Vaccify_Project.entities.Patient;
import Vaccify_Project.Vaccify_Project.exceptions.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Vaccify_Project.Vaccify_Project.repositories.PatientRepository;
import Vaccify_Project.Vaccify_Project.requestDtos.PatientDto;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public String addPatient(PatientDto patientDto) throws PatientNotFoundException
    {
        Patient existingPatient=patientRepository.getByAadharNumber(patientDto.getAadharNumber());
        if(existingPatient!=null)
        {
            throw new PatientNotFoundException("Patient "+existingPatient.getName()+" already registered with this Aadhar Number. \n Kindly try with another Aadhar number.");
        }
        Patient existingPatient2=patientRepository.getByEmailId(patientDto.getEmailId());
        if(existingPatient2!=null)
        {
            throw new PatientNotFoundException("Patient "+existingPatient2.getName()+" already registere using this Mail address. \n Kindly modify your Mail address");
        }
        Patient patient=new Patient();
        patient.setName(patientDto.getName());
        patient.setAge(patientDto.getAge());
        patient.setMobile(patientDto.getMobile());
        patient.setAadharNumber(patientDto.getAadharNumber());
        patient.setGender(patientDto.getGender());
        patient.setEmailId(patientDto.getEmailId());
        patient.setPreferredDose(patientDto.getPreferredDose());

        patientRepository.save(patient);
        return "Patient "+patientDto.getName()+" has been added Successfully !!";
    }

    public Patient getPatientByEmail(String email) throws PatientNotFoundException {
        Patient existingUser = patientRepository.getByEmailId(email);
        if (existingUser==null) {
            throw new PatientNotFoundException("Patient does not exists with this mail address "+email);
        }
        return existingUser;
    }
}
