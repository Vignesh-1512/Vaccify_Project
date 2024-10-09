package Vaccify_Project.Vaccify_Project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Appointment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    private int vaccinationCentreid;
    private String centerName;
    //private int docId;
    private String docName;
    private int patientId;
    private String patientName;
    private LocalDateTime appointmentDateTime;
    private boolean isVaccinated;

    @ManyToOne
    @JoinColumn(name = "doctor_id") // Customize the foreign key column name
    private Doctor doctor;
}
