package Vaccify_Project.Vaccify_Project.services;

import Vaccify_Project.Vaccify_Project.entities.VaccinationCenter;
import Vaccify_Project.Vaccify_Project.exceptions.VaccinationAddressEmptyException;
import Vaccify_Project.Vaccify_Project.repositories.VaccinationCenterRepository;
import Vaccify_Project.Vaccify_Project.requestDtos.VaccinationCenterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VaccinationCenterService {
    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;

    public String addVaccinationCenter(VaccinationCenterDto vaccinationCenterDto) throws VaccinationAddressEmptyException {
        if(Objects.isNull(vaccinationCenterDto.getAddress()) || vaccinationCenterDto.getAddress().trim().isEmpty()) {
            throw new VaccinationAddressEmptyException("Address not found");
        }

        boolean addressExists = vaccinationCenterRepository.existsByAddress(vaccinationCenterDto.getAddress());
        if (addressExists) {
            throw new VaccinationAddressEmptyException("A vaccination center with this address already exists.");
        }
        VaccinationCenter vaccinationCenter=new VaccinationCenter();
        vaccinationCenter.setCenterName(vaccinationCenterDto.getCenterName());
        vaccinationCenter.setCovaxinCount(vaccinationCenterDto.getCovaxinCount());
        vaccinationCenter.setCovishieldCount(vaccinationCenterDto.getCovishieldCount());
        vaccinationCenter.setSputnikCount(vaccinationCenterDto.getSputnikCount());
        vaccinationCenter.setAddress(vaccinationCenterDto.getAddress());

        vaccinationCenterRepository.save(vaccinationCenter);
        return "new Vaccination center added Successfully !!";
    }
}
