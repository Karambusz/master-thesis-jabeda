package edu.agh.jabeda.server.application.port.in.usecase;

import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.domain.ReportedProblem;
import edu.agh.jabeda.server.domain.ReportedProblemId;

import java.util.Collection;
import java.util.List;

public interface ReportProblemUseCase {

    ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest);
    Collection<ReportedProblem> getNewReportedProblemsByCategories(List<String> categories, Integer subscriberId);
    Collection<ReportedProblem> getUserReportedProblemsHistory(String userDeviceId);
    Collection<ReportedProblem> getSubscriberReportedProblemsHistory(Integer subscriberId);
    ReportedProblem updateReportedProblemStatus(Integer reportedProblemId, Integer problemStatusId, Integer subscriberId);
    void banUserByDeviceId(String userDeviceId);
}
