package edu.agh.jabeda.server.application.port.out;

import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.domain.ProblemStatus;
import edu.agh.jabeda.server.domain.ReportedProblem;
import edu.agh.jabeda.server.domain.ReportedProblemAddress;
import edu.agh.jabeda.server.domain.ReportedProblemId;

import java.util.Collection;
import java.util.List;

public interface ReportedProblemPort {
    ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest,
                                    ProblemStatus problemStatus,
                                    ReportedProblemAddress reportedProblemAddress);

    void updateProblemWithImageUrl(String imageUrl, ReportedProblemId reportedProblemId);

    Collection<ReportedProblem> getNewReportedProblemsByCategories(List<String> categories, Integer subscriberId);

    Collection<ReportedProblem> getUserReportedProblemsHistory(String userDeviceId);

    Collection<ReportedProblem> getSubscriberReportedProblemsHistory(Integer subscriberId);

    ReportedProblem updateReportedProblemStatus(Integer reportedProblemId, Integer problemStatusId, Integer subscriberId);

    void banUserByDeviceId(String userDeviceId);

    Integer getRejectedProblemsCount(String userDeviceId);
}
