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

    Collection<ReportedProblem> getNewReportedProblemsByCategories(List<Integer> categories);
}
