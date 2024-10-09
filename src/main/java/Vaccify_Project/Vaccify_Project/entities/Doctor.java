package Vaccify_Project.Vaccify_Project.entities;

import Vaccify_Project.Vaccify_Project.enums.Qualification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Doctor")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int docId;
    private String name;

    @Enumerated(EnumType.STRING)
    private Qualification qualification;

    @Column(unique = true)
    private String emailId;

    @ManyToOne
    @JoinColumn
    private VaccinationCenter vaccinationCenter;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList=new ArrayList<>();
}
