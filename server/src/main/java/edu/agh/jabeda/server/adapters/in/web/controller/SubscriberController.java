package edu.agh.jabeda.server.adapters.in.web.controller;

import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.application.port.in.model.request.UpdateSubscriberRequest;
import edu.agh.jabeda.server.application.port.in.usecase.SubscriberUseCase;
import edu.agh.jabeda.server.domain.Subscriber;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Subscriber API", description = "Contains a set of Subscriber related methods")

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberUseCase subscriberUseCase;

    @Operation(summary = "Create Subscriber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns created Subscriber info",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subscriber.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping
    Subscriber createSubscriber(@Valid @RequestBody CreateSubscriberRequest body) {
        return subscriberUseCase.createSubscriber(body);
    }

    @Operation(summary = "Update Subscriber data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns update Subscriber info",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subscriber.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PutMapping
    Subscriber updateSubscriber(@Valid @RequestBody UpdateSubscriberRequest body) {
        return subscriberUseCase.updateSubscriber(body);
    }


}
