package Vaccify_Project.Vaccify_Project.repositories;

import Vaccify_Project.Vaccify_Project.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
        Patient getByAadharNumber(long aadharNumber);
        Patient getByEmailId(String emailId);
}
