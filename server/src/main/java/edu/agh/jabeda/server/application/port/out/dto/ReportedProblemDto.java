package edu.agh.jabeda.server.application.port.out.dto;

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
    private int idProblemStatus;

    private LocalDateTime reportedDateTime;

    private String description;

    private ReportedProblemAddressDto reportedProblemAddress;

    private ProblemDto problem;

    private ProblemStatusDto problemStatus;

    private ReportedProblemSubscriberDto problemSubscriber;

    private UserDeviceDto userDevice;
}
