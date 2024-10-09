package Vaccify_Project.Vaccify_Project.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationCenterDto {

    private String centerName;

    private int covishieldCount;

    private int covaxinCount;

    private int sputnikCount;

    private String address;
}
