package edu.agh.jabeda.server.domain;

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
public class Subscriber {
    private int idSubscriber;

    private String firstName;

    private String lastName;

    private SubscriberData subscriberData;

    private SubscriberInfo subscriberInfo;

    private Set<Category> categories = new HashSet<>();
}
