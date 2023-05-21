package edu.agh.jabeda.server.application.port.in.model.usecase;

import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.domain.ReportedProblemId;

public interface ReportProblemUseCase {

    ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest);
}
