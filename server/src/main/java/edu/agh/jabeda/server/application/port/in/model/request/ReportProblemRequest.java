package edu.agh.jabeda.server.application.port.in.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportProblemRequest {
    private int category;
    private int problem;
    private String description;
    private LocalDateTime date;
    private  String address;
    private String deviceId;
    private String imageBase64;
}
