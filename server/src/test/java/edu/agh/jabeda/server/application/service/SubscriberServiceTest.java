package edu.agh.jabeda.server.application.service;

import com.google.maps.model.LatLng;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.application.port.out.SubscriberPort;
import edu.agh.jabeda.server.application.service.mapper.SubscriberMapper;
import edu.agh.jabeda.server.domain.Subscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriberServiceTest {

    @Mock
    private SubscriberPort subscriberPort;

    @Mock
    private SubscriberMapper subscriberMapper;

    @Mock
    private GeocodingHelper geocodingHelper;

    @InjectMocks
    private SubscriberService subscriberService;

    @Test
    public void testCreateSubscriber() {
        CreateSubscriberRequest request = new CreateSubscriberRequest("firstName", "LastName",
                "numer", "email", "pass", "country", "city",
                "Street", "123", List.of("category"), Set.of("role"));

        final var subscriberEntity = new SubscriberEntity();
        subscriberEntity.setFirstName("firstName");
        final var subscriber = new Subscriber();
        subscriber.setFirstName("firstName");
        LatLng dummyLatLng = new LatLng(40.7128, -74.0060);
        when(geocodingHelper.getLocation(anyString())).thenReturn(dummyLatLng);
        when(subscriberPort.createSubscriber(eq(request), eq(dummyLatLng))).thenReturn(subscriberEntity);
        when(subscriberMapper.toSubscriber(any())).thenReturn(subscriber);


        Subscriber result = subscriberService.createSubscriber(request);


        verify(geocodingHelper).getLocation(anyString());
        verify(subscriberPort).createSubscriber(eq(request), eq(dummyLatLng));
        verify(subscriberMapper).toSubscriber(any());
        assertEquals("firstName", result.getFirstName());

    }
}

