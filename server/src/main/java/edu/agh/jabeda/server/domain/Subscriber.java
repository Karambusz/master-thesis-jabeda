package edu.agh.jabeda.server.domain;

import java.util.HashSet;
import java.util.Set;

public class Subscriber {
    private int idProblemStatus;

    private String firstName;

    private String lastName;

    private SubscriberData subscriberData;

    private Set<Category> categories = new HashSet<>();
}
