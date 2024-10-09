package Vaccify_Project.Vaccify_Project.repositories;

import Vaccify_Project.Vaccify_Project.entities.Appointment;
import Vaccify_Project.Vaccify_Project.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    Appointment getByPatientId(int patientId);

    List<Appointment> findByDoctor_DocId(int doctorId);

    List<Appointment> findByVaccinationCentreid(int id);
}
