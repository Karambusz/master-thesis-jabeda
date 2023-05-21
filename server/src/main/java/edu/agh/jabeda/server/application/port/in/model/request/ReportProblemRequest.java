package edu.agh.jabeda.server.application.port.in.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ReportProblemRequest {
    private final int category;
    private final int problem;
    private final String description;
    private final LocalDateTime date;
    private final String address;
    private final String deviceId;
}
