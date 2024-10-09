package Vaccify_Project.Vaccify_Project.controllers;

import Vaccify_Project.Vaccify_Project.entities.Patient;
import Vaccify_Project.Vaccify_Project.exceptions.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Vaccify_Project.Vaccify_Project.requestDtos.PatientDto;
import Vaccify_Project.Vaccify_Project.services.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/addPatient")
    public ResponseEntity<String> addPatient(@RequestBody PatientDto patientDto)
    {
        try
        {
            String response = patientService.addPatient(patientDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (PatientNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/get-patient-by-email/{email}")
    public ResponseEntity<Patient> getUserByEmail(@PathVariable String email){
        try
        {
            Patient obj = patientService.getPatientByEmail(email);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }catch(PatientNotFoundException e)
        {
            e.getMessage();
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
