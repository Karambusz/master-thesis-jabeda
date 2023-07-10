package edu.agh.jabeda.server.application.port.out;

import edu.agh.jabeda.server.domain.Subscriber;

public interface AuthPort {
    Subscriber loadUserByEmail(String email);

}
