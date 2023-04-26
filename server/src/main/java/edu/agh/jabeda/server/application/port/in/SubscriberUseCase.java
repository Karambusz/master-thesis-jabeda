package edu.agh.jabeda.server.application.port.in;

import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.application.port.out.dto.SubscriberDto;
import edu.agh.jabeda.server.domain.Subscriber;

import java.util.Collection;

public interface SubscriberUseCase {
    Collection<SubscriberDto> getSubscribers();

    Subscriber createSubscriber(CreateSubscriberRequest body);
}
