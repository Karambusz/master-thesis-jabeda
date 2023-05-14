package edu.agh.jabeda.server.application.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberInfoDto {
    private int idSubscriberInfo;

    private SubscriberDto subscriber;

    private SubscriberAddressDto subscriberAddress;

    private String phoneNumber;
}