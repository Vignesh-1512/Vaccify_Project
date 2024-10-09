package Vaccify_Project.Vaccify_Project.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentDto {

    private int vaccinationCentreid;

    private String centerName;

    private int docId;

    private String docName;

    private int patientId;

    private String patientName;

    private LocalDateTime appointmentDateTime;

    private boolean isVaccinated;
}
