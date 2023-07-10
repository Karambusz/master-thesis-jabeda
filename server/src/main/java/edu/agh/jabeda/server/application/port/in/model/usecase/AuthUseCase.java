package edu.agh.jabeda.server.application.port.in.model.usecase;

import edu.agh.jabeda.server.domain.Subscriber;

public interface AuthUseCase {
    Subscriber loadUserByEmail(String email);

}
