package edu.agh.jabeda.server.application.port.out;

import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.domain.Subscriber;

import java.util.Collection;

public interface SubscriberPort {
    Collection<Subscriber> getSubscribers();

    SubscriberEntity createSubscriber(CreateSubscriberRequest body);
}
