package edu.agh.jabeda.server.adapters.out.persistence.adapter;

import edu.agh.jabeda.server.adapters.out.persistence.repository.SubscriberDataRepository;
import edu.agh.jabeda.server.application.port.out.AuthPort;
import edu.agh.jabeda.server.application.service.mapper.SubscriberDataMapper;
import edu.agh.jabeda.server.common.PersistenceAdapter;
import edu.agh.jabeda.server.domain.SubscriberData;
import edu.agh.jabeda.server.domain.exception.SubscriberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@PersistenceAdapter
public class AuthPersistenceAdapter implements AuthPort {

    private final SubscriberDataRepository subscriberDataRepository;
    private final SubscriberDataMapper subscriberDataMapper;

    @Override
    public SubscriberData loadUserByEmail(String email) {
        final var subscriberData = subscriberDataRepository.findByEmail(email);
        if (subscriberData.isPresent()) {
            return subscriberDataMapper.toSubscriberData(subscriberData.get());
        } else {
            throw new SubscriberNotFoundException(email);
        }
    }
}
