package edu.agh.jabeda.server.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberAddressDto {
    private int idSubscriberAddress;

    private String country;

    private String city;

    private String street;

    private String buildingNumber;

    private double latitude;

    private double longitude;
}
