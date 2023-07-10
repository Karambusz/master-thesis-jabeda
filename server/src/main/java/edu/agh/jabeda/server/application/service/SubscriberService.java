package edu.agh.jabeda.server.application.service;

import com.google.maps.model.LatLng;
import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.application.port.in.model.request.UpdateSubscriberRequest;
import edu.agh.jabeda.server.application.port.in.model.usecase.SubscriberUseCase;
import edu.agh.jabeda.server.application.port.out.SubscriberPort;
import edu.agh.jabeda.server.application.service.mapper.SubscriberMapper;
import edu.agh.jabeda.server.common.UseCase;
import edu.agh.jabeda.server.domain.Subscriber;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class SubscriberService implements SubscriberUseCase {
    private final SubscriberPort subscriberPort;
    private final SubscriberMapper subscriberMapper;
    private final GeocodingHelper geocodingHelper;


    @Override
    public Subscriber createSubscriber(CreateSubscriberRequest body) {
        final var address = String.join(", ", body.getCountry(), body.getCity(), body.getStreet(), body.getBuildingNumber());
        LatLng latLng = geocodingHelper.getLocation(address);
        System.out.println(latLng);
        return subscriberMapper.toSubscriber(subscriberPort.createSubscriber(body, latLng));
    }

    @Override
    public Subscriber updateSubscriber(UpdateSubscriberRequest body) {
        final var address = String.join(", ", body.getCountry(), body.getCity(), body.getStreet(), body.getBuildingNumber());
        LatLng latLng = geocodingHelper.getLocation(address);
        return subscriberMapper.toSubscriber(subscriberPort.updateSubscriber(body, latLng));
    }
}
