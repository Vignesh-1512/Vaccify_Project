package Vaccify_Project.Vaccify_Project.entities;

import Vaccify_Project.Vaccify_Project.enums.Dosage;
import Vaccify_Project.Vaccify_Project.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Patient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name of patient")
    private String name;
    private int age;
    private long mobile;

    @Column(unique = true)
    private long aadharNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(unique = true)
    private String emailId;
    @Enumerated(EnumType.STRING)
    private Dosage preferredDose;


}
