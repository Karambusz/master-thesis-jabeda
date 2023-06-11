package edu.agh.jabeda.server.adapters.in.web.controller;

import edu.agh.jabeda.server.adapters.in.web.dto.ReportedProblemDto;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.in.model.request.ReportedProblemsByCategoriesRequest;
import edu.agh.jabeda.server.application.port.in.model.usecase.ReportProblemUseCase;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@Tag(name = "Reported Problem API", description = "Contains a set of reported problem related methods")
@RequiredArgsConstructor
@RestController
@RequestMapping("/reported-problems")
public class ReportedProblemController {

    private final ReportProblemUseCase reportProblemUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create reported problem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns an id of created reported problem",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportedProblemId.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    ReportedProblemId reportProblem(
            @Valid @RequestBody ReportProblemRequest reportProblemRequest) {
        return reportProblemUseCase.reportProblem(reportProblemRequest);
    }


    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all pending reported problems")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns  all pending reported problems",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportedProblemId.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping
    Collection<ReportedProblemDto> getNewReportedProblemsByCategories(
             @RequestBody ReportedProblemsByCategoriesRequest categories) {
        return reportProblemUseCase.getNewReportedProblemsByCategories(categories);
    }
}
