package edu.agh.jabeda.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberInfo {
    private int idSubscriberInfo;

    private Subscriber subscriber;

    private SubscriberAddress subscriberAddress;

    private String phoneNumber;
}
