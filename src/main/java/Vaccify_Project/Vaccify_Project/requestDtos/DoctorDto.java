package Vaccify_Project.Vaccify_Project.requestDtos;

import Vaccify_Project.Vaccify_Project.enums.Qualification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DoctorDto {

    private String name;

    private Qualification qualification;

    private String emailId;

    private int vaccination_center_id;
}
