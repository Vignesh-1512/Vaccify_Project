package Vaccify_Project.Vaccify_Project.services;

import Vaccify_Project.Vaccify_Project.entities.Doctor;
import Vaccify_Project.Vaccify_Project.entities.VaccinationCenter;
import Vaccify_Project.Vaccify_Project.enums.Qualification;
import Vaccify_Project.Vaccify_Project.exceptions.DoctorAlreadyExistException;
import Vaccify_Project.Vaccify_Project.exceptions.EmailInvalidException;
import Vaccify_Project.Vaccify_Project.repositories.DoctorRepository;
import Vaccify_Project.Vaccify_Project.repositories.VaccinationCenterRepository;
import Vaccify_Project.Vaccify_Project.requestDtos.DoctorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;

    public String addDoctor(DoctorDto doctorDto) throws EmailInvalidException, DoctorAlreadyExistException
    {
        if(Objects.isNull(doctorDto.getEmailId()) || doctorDto.getEmailId().equals("")) throw new EmailInvalidException("Invalid Email Id");
        Doctor doc=doctorRepository.findByEmailId(doctorDto.getEmailId());
        if(doc!=null)
        {
            throw new DoctorAlreadyExistException("Doctor already exist in the vaccine portal");
        }
        Doctor doctor=new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setQualification(doctorDto.getQualification());
        doctor.setEmailId(doctorDto.getEmailId());
        VaccinationCenter vaccinationCenter = vaccinationCenterRepository.findById(doctorDto.getVaccination_center_id())
                .orElseThrow(() -> new RuntimeException("Vaccination center not found"));
        doctor.setVaccinationCenter(vaccinationCenter);
        doctorRepository.save(doctor);
        return "Doctor "+doctor.getName()+" has been added successfully !!";
    }

    public List<String> getDoctorList(int id)
    {
        List<Doctor> doctorList=vaccinationCenterRepository.findById(id).get().getDoctorList();
        List<String> list=new ArrayList<>();
        for (Doctor doctor:doctorList)
        {
            list.add(doctor.getName());
        }
        return  list;
    }

    public List<String> getDoctorsbasedOnQualification(String qualification)
    {
        List<Doctor> doctors=doctorRepository.findByQualification(qualification);
            if (doctors == null) {
                doctors = new ArrayList<>();
            }
            List<String> docNames=new ArrayList<>();
            for(Doctor doctor:doctors)
            {
                docNames.add(doctor.getName());
            }
            return docNames;
    }
}
