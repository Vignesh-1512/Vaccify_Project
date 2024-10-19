package Vaccify_Project.Vaccify_Project.controllers;

import Vaccify_Project.Vaccify_Project.exceptions.EmailInvalidException;
import Vaccify_Project.Vaccify_Project.requestDtos.DoctorDto;
import Vaccify_Project.Vaccify_Project.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    public DoctorService doctorService;

    @PostMapping("/addDoctor")

    public ResponseEntity<String> addDoctor(@RequestBody DoctorDto doctorDto)
    {
        try{
            String response=doctorService.addDoctor(doctorDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getDoctorsList/{id}")
    public List<String> getDoctorsList(@PathVariable Integer id)
    {
        return doctorService.getDoctorList(id);
    }

    @GetMapping("/doctorsQualification")
    public List<String> getDoctorsbasedOnQualification(@RequestParam String qualification) {

        return doctorService.getDoctorsbasedOnQualification(qualification);
    }
}
