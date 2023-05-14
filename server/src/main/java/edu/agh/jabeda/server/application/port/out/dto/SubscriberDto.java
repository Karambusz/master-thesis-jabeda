package edu.agh.jabeda.server.application.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberDto {
    private int idProblemStatus;

    private String firstName;

    private String lastName;

    private SubscriberDataDto subscriberData;

    private Set<CategoryDto> categories = new HashSet<>();

}