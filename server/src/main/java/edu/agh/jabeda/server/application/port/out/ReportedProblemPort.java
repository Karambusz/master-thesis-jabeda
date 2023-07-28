package edu.agh.jabeda.server.application.port.out;

import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.domain.ProblemStatus;
import edu.agh.jabeda.server.domain.ReportedProblemAddress;
import edu.agh.jabeda.server.domain.ReportedProblemId;

import java.util.Collection;
import java.util.List;

public interface ReportedProblemPort {
    ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest,
                                    ProblemStatus problemStatus,
                                    ReportedProblemAddress reportedProblemAddress);

    void updateProblemWithImageUrl(String imageUrl, ReportedProblemId reportedProblemId);

    Collection<ReportedProblemEntity> getNewReportedProblemsByCategories(List<String> categories, Integer subscriberId);

    Collection<ReportedProblemEntity> getUserReportedProblemsHistory(String userDeviceId);

    Collection<ReportedProblemEntity> getSubscriberReportedProblemsHistory(Integer subscriberId);

    ReportedProblemEntity updateReportedProblemStatus(Integer reportedProblemId, Integer problemStatusId, Integer subscriberId);

    void banUserByDeviceId(String userDeviceId);

    Integer getRejectedProblemsCount(String userDeviceId);
}
