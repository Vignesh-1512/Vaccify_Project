package Vaccify_Project.Vaccify_Project.controllers;

import Vaccify_Project.Vaccify_Project.requestDtos.VaccinationCenterDto;
import Vaccify_Project.Vaccify_Project.services.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vaccinationCenter")
public class VaccinationCenterController {

    @Autowired
    private VaccinationCenterService vaccinationCenterService;

    @PostMapping("/add-center")
    public ResponseEntity<String> addVaccinationCenter(@RequestBody VaccinationCenterDto vaccinationCenterDto)
    {
        try{
            String response = vaccinationCenterService.addVaccinationCenter(vaccinationCenterDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
