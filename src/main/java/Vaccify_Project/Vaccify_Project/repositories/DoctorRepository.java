package Vaccify_Project.Vaccify_Project.repositories;

import Vaccify_Project.Vaccify_Project.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    Doctor findByEmailId(String emailId);
    List<Doctor> findByQualification(String qualification);
}
