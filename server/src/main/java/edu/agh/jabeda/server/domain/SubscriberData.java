package edu.agh.jabeda.server.domain;

import edu.agh.jabeda.server.application.port.out.dto.SubscriberDto;

import java.util.HashSet;
import java.util.Set;

public class SubscriberData {
    private SubscriberDto subscriber;

    private String email;

    private String password;

    private Set<Role> roles = new HashSet<>();
}
