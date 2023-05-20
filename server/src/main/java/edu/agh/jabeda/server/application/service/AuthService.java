package edu.agh.jabeda.server.application.service;

import edu.agh.jabeda.server.application.port.in.model.usecase.AuthUseCase;
import edu.agh.jabeda.server.application.port.out.AuthPort;
import edu.agh.jabeda.server.common.UseCase;
import edu.agh.jabeda.server.domain.SubscriberData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AuthService implements AuthUseCase {

    private final AuthPort authPort;

    @Override
    public SubscriberData loadUserByEmail(String email) {
        return authPort.loadUserByEmail(email);
    }
}
