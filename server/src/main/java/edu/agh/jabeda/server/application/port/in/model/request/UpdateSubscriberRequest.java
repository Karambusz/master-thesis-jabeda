package edu.agh.jabeda.server.application.port.in.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubscriberRequest {
    private String firstName;
    private String lastName;
    private String number;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String country;
    private String city;
    private String street;
    private String buildingNumber;
    private List<String> categories;
}
