package edu.agh.jabeda.server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportedProblemAddress {
    private int idSubscriberAddress;

    private String address;

    private double latitude;

    private double longitude;

    public ReportedProblemAddress(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
