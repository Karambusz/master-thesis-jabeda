package edu.agh.jabeda.server.adapters.out.persistence.adapter;

import edu.agh.jabeda.server.adapters.out.persistence.repository.SubscriberRepository;
import edu.agh.jabeda.server.application.port.out.AuthPort;
import edu.agh.jabeda.server.application.service.mapper.SubscriberMapper;
import edu.agh.jabeda.server.common.PersistenceAdapter;
import edu.agh.jabeda.server.domain.Subscriber;
import edu.agh.jabeda.server.domain.exception.SubscriberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@PersistenceAdapter
public class AuthPersistenceAdapter implements AuthPort {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberMapper subscriberMapper;

    @Override
    public Subscriber loadUserByEmail(String email) {
        final var subscriber = subscriberRepository.findBySubscriberDataEmail(email);
        if (subscriber.isPresent()) {
            return subscriberMapper.toSubscriber(subscriber.get());
        } else {
            throw new SubscriberNotFoundException(email);
        }
    }
}
