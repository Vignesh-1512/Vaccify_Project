package Vaccify_Project.Vaccify_Project.requestDtos;

import Vaccify_Project.Vaccify_Project.enums.Dosage;
import Vaccify_Project.Vaccify_Project.enums.Gender;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PatientDto {
    private String name;

    private int age;

    private long mobile;

    private long aadharNumber;

    private Gender gender;

    private String emailId;

    private Dosage preferredDose;
}
