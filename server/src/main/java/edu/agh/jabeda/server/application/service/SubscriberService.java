package edu.agh.jabeda.server.application.service;

import edu.agh.jabeda.server.application.port.in.model.usecase.SubscriberUseCase;
import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.application.port.out.SubscriberPort;
import edu.agh.jabeda.server.adapters.in.web.dto.SubscriberDto;
import edu.agh.jabeda.server.application.service.mapper.SubscriberMapper;
import edu.agh.jabeda.server.common.UseCase;
import edu.agh.jabeda.server.domain.Subscriber;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
@UseCase
@Transactional
public class SubscriberService implements SubscriberUseCase {
    private final SubscriberPort subscriberPort;
    private final SubscriberMapper subscriberMapper;

    @Override
    public Collection<SubscriberDto> getSubscribers() {
        Collection<Subscriber> subscribers = subscriberPort.getSubscribers();
        return new HashSet<>();
    }

    @Override
    public Subscriber createSubscriber(CreateSubscriberRequest body) {
        return subscriberMapper.toSubscriber(subscriberPort.createSubscriber(body));
    }
}
