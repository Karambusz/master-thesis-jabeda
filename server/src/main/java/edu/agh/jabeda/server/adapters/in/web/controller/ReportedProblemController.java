package edu.agh.jabeda.server.adapters.in.web.controller;

import edu.agh.jabeda.server.adapters.in.web.dto.ReportedProblemDto;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.in.model.usecase.ReportProblemUseCase;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Tag(name = "Reported Problem API", description = "Contains a set of reported problem related methods")
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/reported-problems")
public class ReportedProblemController {

    private final ReportProblemUseCase reportProblemUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create reported problem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully returns an id of created reported problem",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportedProblemId.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    ReportedProblemId reportProblem(
            @Valid @RequestBody ReportProblemRequest reportProblemRequest) {
        return reportProblemUseCase.reportProblem(reportProblemRequest);
    }

    @PatchMapping(path = "/{reportedProblemId}/status")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update reported problem status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully update reported problem status and assign subscriber to problem",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportedProblemDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    ReportedProblemDto updateReportedProblemStatus(@PathVariable Integer reportedProblemId,
             @RequestParam Integer problemStatusId,
             @RequestParam Integer subscriberId
    ) {
        return reportProblemUseCase.updateReportedProblemStatus(reportedProblemId, problemStatusId, subscriberId);
    }

    @PatchMapping(path = "/users/{userDeviceId}/ban")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Ban user by device id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully banned user by device id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportedProblemDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    void banUserByDeviceId(@PathVariable String userDeviceId) {
        reportProblemUseCase.banUserByDeviceId(userDeviceId);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all pending reported problems")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns  all pending reported problems",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportedProblemDto.class))) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping
    Collection<ReportedProblemDto> getNewReportedProblemsByCategories(@Valid @RequestParam List<String> category,
                                                                      @Valid @RequestParam Integer subscriberId) {
        return reportProblemUseCase.getNewReportedProblemsByCategories(category, subscriberId);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user reported problems history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns all user reported problems",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportedProblemDto.class))) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping(path="/user-history")
    Collection<ReportedProblemDto> getUserReportedProblemsHistory(@Valid @RequestParam String userDeviceId) {
        return reportProblemUseCase.getUserReportedProblemsHistory(userDeviceId);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get subscriber reported problems history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns all processed by subscriber reported problems",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportedProblemDto.class))) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping(path="/subscriber-history")
    Collection<ReportedProblemDto> getSubscriberReportedProblemsHistory(@Valid @RequestParam Integer subscriberId) {
        return reportProblemUseCase.getSubscriberReportedProblemsHistory(subscriberId);

    }
}
