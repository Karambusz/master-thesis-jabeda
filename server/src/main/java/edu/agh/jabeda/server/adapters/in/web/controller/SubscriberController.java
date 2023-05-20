package edu.agh.jabeda.server.adapters.in.web.controller;

import edu.agh.jabeda.server.application.port.in.model.usecase.SubscriberUseCase;
import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.adapters.in.web.dto.SubscriberDto;
import edu.agh.jabeda.server.domain.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.Collection;


@RequiredArgsConstructor
@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberUseCase subscriberUseCase;

    @GetMapping
    Collection<SubscriberDto> getSubscribers() {
        return subscriberUseCase.getSubscribers();
    }

    @PostMapping
    Subscriber createSubscriber(@Valid @RequestBody CreateSubscriberRequest body) {
        return subscriberUseCase.createSubscriber(body);
    }





}
