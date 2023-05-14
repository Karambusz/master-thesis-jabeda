package edu.agh.jabeda.server.application.port.in;

import edu.agh.jabeda.server.domain.SubscriberData;

public interface AuthUseCase {
    SubscriberData loadUserByEmail(String email);

}
