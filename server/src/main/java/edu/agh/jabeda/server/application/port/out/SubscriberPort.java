package edu.agh.jabeda.server.application.port.out;

import com.google.maps.model.LatLng;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.application.port.in.model.request.UpdateSubscriberRequest;

public interface SubscriberPort {

    SubscriberEntity createSubscriber(CreateSubscriberRequest body, LatLng latLng);

    SubscriberEntity updateSubscriber(UpdateSubscriberRequest body, LatLng latLng);
}
