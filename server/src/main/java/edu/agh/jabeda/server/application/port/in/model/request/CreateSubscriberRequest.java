package edu.agh.jabeda.server.application.port.in.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubscriberRequest {
    private String firstName;
    private String lastName;
    private String number;
    private String email;
    private String password;
    private String country;
    private String city;
    private String street;
    private String buildingNumber;
    private List<String> categories;
    private Set<String> role;
}
