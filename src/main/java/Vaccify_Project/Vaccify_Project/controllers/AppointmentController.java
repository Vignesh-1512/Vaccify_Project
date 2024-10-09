package Vaccify_Project.Vaccify_Project.controllers;

import Vaccify_Project.Vaccify_Project.entities.Appointment;
import Vaccify_Project.Vaccify_Project.requestDtos.AppointmentDto;
import Vaccify_Project.Vaccify_Project.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/addAppointment")
    public ResponseEntity<String> addAppointment(@RequestBody AppointmentDto appointmentDto)
    {
        try{
            String response=appointmentService.addAppointment(appointmentDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllAppointmentsOfDoctor/{id}")
    public ResponseEntity<List<String>> getAllAppointmentsOfDoctor(@PathVariable Integer id){
        List<String> list = appointmentService.getAllAppointmentsofDoctor(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getAppointmentByPatientId/{id}")
    public AppointmentDto getAppointment(@PathVariable Integer id)
    {
        return appointmentService.getAppointmentByPatientId(id);
    }

    @GetMapping("/getAppointmentByCenterId/{id}")
    public List<String> getAppointmentByCenter(@PathVariable Integer id)
    {
        return appointmentService.getAppointmentByvaccinationCenterId(id);
    }
    @PostMapping("vaccination/{id}")
    public String processVaccination(@PathVariable Integer id)
    {
        return appointmentService.processVaccination(id);
    }
}
