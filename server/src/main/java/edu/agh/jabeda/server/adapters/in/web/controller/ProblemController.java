package edu.agh.jabeda.server.adapters.in.web.controller;

import edu.agh.jabeda.server.adapters.in.web.dto.CategoryProblemsDto;
import edu.agh.jabeda.server.adapters.in.web.dto.ProblemStatusDto;
import edu.agh.jabeda.server.adapters.in.web.dto.mapper.CategoryProblemsDtoMapper;
import edu.agh.jabeda.server.application.port.in.usecase.ProblemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "Problem API", description = "Contains a set of problems and category related methods")

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping
public class ProblemController {
    private final ProblemUseCase problemUseCase;

    @Operation(summary = "Get predefined problems and categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns existing problems",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CategoryProblemsDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping("/problems")
    Collection<CategoryProblemsDto> getProblems() {
        return CategoryProblemsDtoMapper.create()
                .mapFromDomain(problemUseCase.getProblems());
    }

    @Operation(summary = "Get predefined problem statuses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returns existing problem statuses",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProblemStatusDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping("/problem-statuses")
    Collection<ProblemStatusDto> getProblemStatuses() {
        return problemUseCase.getProblemStatuses();
    }


}
