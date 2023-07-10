package edu.agh.jabeda.server.application.port.in.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String token;
    private final String type = "Bearer";
    private final Integer id;
    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String number;
    private final String country;
    private final String city;
    private final String street;
    private final String buildingNumber;
    private final Double latitude;
    private final Double longitude;
    private final List<String> categories;
    private final List<String> roles;
}
