package edu.agh.jabeda.server.application.port.in.model.usecase;

import edu.agh.jabeda.server.adapters.in.web.dto.ReportedProblemDto;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.in.model.request.ReportedProblemsByCategoriesRequest;
import edu.agh.jabeda.server.domain.ReportedProblemId;

import java.util.Collection;

public interface ReportProblemUseCase {

    ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest);

    Collection<ReportedProblemDto> getNewReportedProblemsByCategories(ReportedProblemsByCategoriesRequest categories);
}
