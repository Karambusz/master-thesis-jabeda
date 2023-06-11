package edu.agh.jabeda.server.application.service;

import com.google.maps.model.LatLng;
import edu.agh.jabeda.server.adapters.in.web.dto.ReportedProblemDto;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.in.model.request.ReportedProblemsByCategoriesRequest;
import edu.agh.jabeda.server.application.port.in.model.usecase.ReportProblemUseCase;
import edu.agh.jabeda.server.application.port.out.ReportedProblemPort;
import edu.agh.jabeda.server.application.service.mapper.ReportedProblemMapper;
import edu.agh.jabeda.server.common.UseCase;
import edu.agh.jabeda.server.domain.ProblemStatus;
import edu.agh.jabeda.server.domain.ReportedProblemAddress;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import edu.agh.jabeda.server.domain.SupportedProblemStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
@UseCase
@Transactional
public class ReportedProblemService implements ReportProblemUseCase {

    private final ReportedProblemPort reportedProblemPort;
    private final GeocodingHelper geocodingHelper;
    private final ReportedProblemMapper reportedProblemMapper;

    @Override
    public ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest) {
        ProblemStatus problemStatus = new ProblemStatus(SupportedProblemStatus.PENDING);

        LatLng latLng = geocodingHelper.getLocation(reportProblemRequest.getAddress());
        ReportedProblemAddress reportedProblemAddress =
                new ReportedProblemAddress(reportProblemRequest.getAddress(), latLng.lat, latLng.lng);
        return reportedProblemPort.reportProblem(reportProblemRequest, problemStatus, reportedProblemAddress);
    }

    @Override
    public Collection<ReportedProblemDto> getNewReportedProblemsByCategories(ReportedProblemsByCategoriesRequest categories) {
        return reportedProblemMapper.toReportedProblemDtos(
                reportedProblemPort.getNewReportedProblemsByCategories(categories.getCategories())
        );
    }
}
