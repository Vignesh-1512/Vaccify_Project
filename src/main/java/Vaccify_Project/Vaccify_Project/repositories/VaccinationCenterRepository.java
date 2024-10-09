package Vaccify_Project.Vaccify_Project.repositories;

import Vaccify_Project.Vaccify_Project.entities.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter,Integer> {
    boolean existsByAddress(String address);
}
