package edu.agh.jabeda.server.adapters.in.web.controller;

import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.in.model.usecase.ReportProblemUseCase;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reported-problems")
public class ReportedProblemController {

    private final ReportProblemUseCase reportProblemUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ReportedProblemId reportProblem(
            @Valid @RequestBody ReportProblemRequest reportProblemRequest) {
        return reportProblemUseCase.reportProblem(reportProblemRequest);
    }

}
