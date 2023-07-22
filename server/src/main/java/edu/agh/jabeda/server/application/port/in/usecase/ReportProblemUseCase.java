package edu.agh.jabeda.server.application.port.in.usecase;

import edu.agh.jabeda.server.adapters.in.web.dto.ReportedProblemDto;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.domain.ReportedProblemId;

import java.util.Collection;
import java.util.List;

public interface ReportProblemUseCase {

    ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest);

    Collection<ReportedProblemDto> getNewReportedProblemsByCategories(List<String> categories, Integer subscriberId);
    Collection<ReportedProblemDto> getUserReportedProblemsHistory(String userDeviceId);
    Collection<ReportedProblemDto> getSubscriberReportedProblemsHistory(Integer subscriberId);
    ReportedProblemDto updateReportedProblemStatus(Integer reportedProblemId, Integer problemStatusId, Integer subscriberId);
    void banUserByDeviceId(String userDeviceId);
}
