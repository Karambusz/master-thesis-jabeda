package edu.agh.jabeda.server.application.port.in.model.usecase;

import edu.agh.jabeda.server.domain.SubscriberData;

public interface AuthUseCase {
    SubscriberData loadUserByEmail(String email);

}
