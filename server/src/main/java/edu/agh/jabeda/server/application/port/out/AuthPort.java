package edu.agh.jabeda.server.application.port.out;

import edu.agh.jabeda.server.domain.SubscriberData;

public interface AuthPort {
    SubscriberData loadUserByEmail(String email);

}
