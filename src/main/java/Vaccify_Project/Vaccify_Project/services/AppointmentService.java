package Vaccify_Project.Vaccify_Project.services;

import Vaccify_Project.Vaccify_Project.entities.Appointment;
import Vaccify_Project.Vaccify_Project.entities.Doctor;
import Vaccify_Project.Vaccify_Project.entities.Patient;
import Vaccify_Project.Vaccify_Project.entities.VaccinationCenter;
import Vaccify_Project.Vaccify_Project.enums.Dosage;
import Vaccify_Project.Vaccify_Project.exceptions.*;
import Vaccify_Project.Vaccify_Project.repositories.AppointmentRepository;
import Vaccify_Project.Vaccify_Project.repositories.DoctorRepository;
import Vaccify_Project.Vaccify_Project.repositories.PatientRepository;
import Vaccify_Project.Vaccify_Project.repositories.VaccinationCenterRepository;
import Vaccify_Project.Vaccify_Project.requestDtos.AppointmentDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired VaccinationCenterService vaccinationCenterService;
    @Autowired DoctorService doctorService;
    @Autowired PatientService patientService;

    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    JavaMailSender javaMailSender;


    public String addAppointment(AppointmentDto appointmentDto) throws DoctorNotFoundException, PatientNotFoundException, CenterNotFoundException, AppointmentAlreadyDoneException, DosageNotAvailableException {
        Optional<Doctor> doctorOptional=doctorRepository.findById(appointmentDto.getDocId());
        if(doctorOptional.isEmpty()){
            throw new DoctorNotFoundException("Doctor with Id " +appointmentDto.getDocId()+" is not present in the system !!");
        }
        else{
            VaccinationCenter vaccinationCenter=doctorOptional.get().getVaccinationCenter();
            if(vaccinationCenter.getCenterId()!=appointmentDto.getVaccinationCentreid())
            {
                throw new DoctorNotFoundException("Doctor with ID "+appointmentDto.getDocId()+" is not associated with Vaccination Center with ID "
                +appointmentDto.getVaccinationCentreid());
            }
        }

        Optional<Patient> optionalPatient=patientRepository.findById(appointmentDto.getPatientId());
        if(optionalPatient.isEmpty())
        {
            throw new PatientNotFoundException("Patient with ID "+appointmentDto.getPatientId()+" is notpresent in the system");
        }

        Optional<VaccinationCenter> optionalVaccinationCenter=vaccinationCenterRepository.findById(appointmentDto.getVaccinationCentreid());
        if(optionalVaccinationCenter.isEmpty())
        {
            throw new CenterNotFoundException("Vaccination Center wit ID "+appointmentDto.getVaccinationCentreid()+" is not present in the system !!");
        }

        Doctor doctor=doctorOptional.get();
        Patient patient=optionalPatient.get();
        VaccinationCenter vaccinationCenter=optionalVaccinationCenter.get();

        Appointment existingAppointment=appointmentRepository.getByPatientId(patient.getId());
        if(existingAppointment!=null)
        {
            throw new AppointmentAlreadyDoneException(patient.getName()+" has already made an appointment at "+vaccinationCenter.getCenterName()+" with Doctor "+doctor.getName());
        }

        Dosage dosage=patient.getPreferredDose();
        if((dosage.equals(Dosage.Sputnik)&&vaccinationCenter.getSputnikCount()<1)||
        (dosage.equals(Dosage.Covaxin)&&vaccinationCenter.getCovaxinCount()<1)||
                (dosage.equals(Dosage.Covishield)&&vaccinationCenter.getCovishieldCount()<1))
        {
            throw new DosageNotAvailableException(dosage.toString()+" Dose is not available currently at "+vaccinationCenter.getCenterName());
        }
        if(dosage.equals(Dosage.Sputnik) && vaccinationCenter.getSputnikCount()>0)
        {
            vaccinationCenter.setSputnikCount(vaccinationCenter.getSputnikCount()-1);
        }
        else if(dosage.equals(Dosage.Covishield) && vaccinationCenter.getCovishieldCount()>0)
        {
            vaccinationCenter.setCovishieldCount(vaccinationCenter.getCovishieldCount()-1);
        }
        else if(dosage.equals(Dosage.Covaxin) && vaccinationCenter.getCovaxinCount()>0)
        {
            vaccinationCenter.setCovaxinCount(vaccinationCenter.getCovaxinCount()-1);
        }

        LocalDateTime currentDateTime=LocalDateTime.now();
        Appointment appointment=new Appointment();
        appointment.setAppointmentDateTime(currentDateTime.plusHours(24));
        appointment.setVaccinated(false);
        appointment.setCenterName(vaccinationCenter.getCenterName());
        appointment.setDocName(doctor.getName());
        appointment.setPatientName(patient.getName());
        //appointment.setDocId(doctor.getDocId());
        appointment.setPatientId(patient.getId());
        appointment.setVaccinationCentreid(vaccinationCenter.getCenterId());
        appointment.setDoctor(doctor);

        appointmentRepository.save(appointment);

        // Update doctor's appointment list and save doctor
        doctor.getAppointmentList().add(appointment);
        doctorRepository.save(doctor);

        String mail = patient.getEmailId();
        String text = "Hey! Your appointment has been booked.\n\n" +
                "Kindly find the below details of your appointment.\n\n" +
                "Vaccination Center: "+vaccinationCenter.getCenterName()+
                "\n\n Doctor :"+doctor.getName()+"\n\n Appointment Date & Time :"
                +currentDateTime.plusHours(24);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("vaccinationjavaproject@gmail.com");
        simpleMailMessage.setTo(patient.getEmailId());
        simpleMailMessage.setSubject("Vaccination Appointment update");
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);



        return "Appointment successfully booked for patient " + patient.getName() + " with doctor " + doctor.getName() + " at center " + vaccinationCenter.getCenterName();
    }

    public List<String> getAllAppointmentsofDoctor(Integer id)
    {
        List<Appointment> appointmentList=appointmentRepository.findByDoctor_DocId(id);
        List<String> list=new ArrayList<>();
        for(Appointment appointment:appointmentList)
        {
            list.add(appointment.getPatientName());
        }
        return  list;
    }

    public AppointmentDto getAppointmentByPatientId(Integer id)
    {
        Appointment appointment= appointmentRepository.getByPatientId(id);
        if(appointment!=null)
        {
            AppointmentDto appointmentDto = new AppointmentDto();
            appointmentDto.setVaccinationCentreid(appointment.getVaccinationCentreid());
            appointmentDto.setCenterName(appointment.getCenterName());
            appointmentDto.setDocId(appointment.getDoctor().getDocId());  // Assuming `docId` is part of the Doctor entity
            appointmentDto.setDocName(appointment.getDocName());
            appointmentDto.setPatientId(appointment.getPatientId());
            appointmentDto.setPatientName(appointment.getPatientName());
            appointmentDto.setAppointmentDateTime(appointment.getAppointmentDateTime());
            appointmentDto.setVaccinated(appointment.isVaccinated());

            return appointmentDto;
        }
        return null;
    }

    public List<String> getAppointmentByvaccinationCenterId(Integer id)
    {
        List<Appointment> appointmentList=appointmentRepository.findByVaccinationCentreid(id);
        List<String> list=new ArrayList<>();
        for (Appointment appointment:appointmentList)
        {
            list.add(appointment.getPatientName()+" has made an appointment with Doctor "+appointment.getDocName()+" for "+appointment.getAppointmentDateTime());
        }
        return list;
    }
    public String processVaccination(Integer id)
    {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if(patientOptional.isEmpty())
            throw new PatientNotFoundException("Patient with ID "+id+ " is not present in the system");

        Appointment appointment = appointmentRepository.getByPatientId(id);
        Optional<VaccinationCenter> vaccinationCenterOptional = vaccinationCenterRepository.findById(appointment.getVaccinationCentreid());
        if(vaccinationCenterOptional.isEmpty())
        {
            throw new CenterNotFoundException("Vaccination Center with ID "+appointment.getVaccinationCentreid()+" is not present in the system");
        }

        Optional<Doctor> doctorOptional =  doctorRepository.findById(appointment.getDoctor().getDocId());
        if(doctorOptional.isEmpty())
        {
            throw new DoctorNotFoundException("Doctor with ID " +appointment.getDoctor().getDocId()+" is not present in the system");
        }
        Doctor doctor = doctorOptional.get();
        VaccinationCenter vaccinationCenter = vaccinationCenterOptional.get();
        Patient patient = patientOptional.get();

        if(appointment.isVaccinated())
        {
            throw new AlreadyVaccinatedException("Patient has been already vaccinated");
        }
        appointment.setVaccinated(true);
        appointmentRepository.save(appointment);

        String htmlContent = "<html>" +
                "<body>" +
                "<p style='font-size:30px; font-weight:bold;'>Congratulations! You are vaccinated.</p>" +
                "<br>" +
                "<p style='font-size:14px;'>This is to certify that you have been vaccinated successfully at " +
                "<span style='font-size:14px; font-weight:bold;'>" + vaccinationCenter.getCenterName() + "</span>" +
                " by Doctor " +
                "<span style='font-size:14px; font-weight:bold;'>" + doctor.getName() + "</span>" +
                " on " +
                "<span style='font-size:14px;'>" + appointment.getAppointmentDateTime() + "</span>" +
                ".</p>" +
                "</body>" +
                "</html>";

        try {
            sendHtmlEmail(patient.getEmailId(), "Vaccination Successful update", htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error sending email: " + e.getMessage();
        }
        return "Patient " + patient.getName() + " has been successfully vaccinated at " + vaccinationCenter.getCenterName() + " by Doctor " + doctor.getName() +" and mail had been sent !!" ;
    }
    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("vaccinationjavaproject@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }
}

