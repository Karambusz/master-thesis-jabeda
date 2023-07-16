package edu.agh.jabeda.server.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportedProblemDto {
    private int idReportedProblem;

    private LocalDateTime reportedDateTime;

    private String description;

    private String imageUrl;

    private ReportedProblemAddressDto reportedProblemAddress;

    private ProblemDto problem;

    private String problemStatusCode;

    private String userDeviceId;

    private Integer rejectedProblemsCount;
}
