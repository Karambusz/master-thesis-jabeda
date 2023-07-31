package edu.agh.jabeda.server.application.port.in.usecase;

import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.application.port.in.model.request.UpdateSubscriberRequest;
import edu.agh.jabeda.server.domain.Subscriber;

public interface SubscriberUseCase {

    Subscriber createSubscriber(CreateSubscriberRequest body);

    Subscriber updateSubscriber(UpdateSubscriberRequest body);
}
