package edu.agh.jabeda.server.application.service;

import com.google.maps.model.LatLng;
import edu.agh.jabeda.server.adapters.in.web.dto.ReportedProblemDto;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.in.model.usecase.ReportProblemUseCase;
import edu.agh.jabeda.server.application.port.out.ImageStoragePort;
import edu.agh.jabeda.server.application.port.out.ReportedProblemPort;
import edu.agh.jabeda.server.application.service.mapper.ReportedProblemMapper;
import edu.agh.jabeda.server.common.UseCase;
import edu.agh.jabeda.server.domain.ProblemStatus;
import edu.agh.jabeda.server.domain.ReportedProblemAddress;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import edu.agh.jabeda.server.domain.SupportedProblemStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@UseCase
@Transactional
public class ReportedProblemService implements ReportProblemUseCase {

    private final ReportedProblemPort reportedProblemPort;
    private final ImageStoragePort imageStoragePort;
    private final GeocodingHelper geocodingHelper;
    private final ReportedProblemMapper reportedProblemMapper;

    @Override
    public ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest) {
        ProblemStatus problemStatus = new ProblemStatus(SupportedProblemStatus.PENDING);

        LatLng latLng = geocodingHelper.getLocation(reportProblemRequest.getAddress());
        ReportedProblemAddress reportedProblemAddress =
                new ReportedProblemAddress(reportProblemRequest.getAddress(), latLng.lat, latLng.lng);

        final var reportedProblemId = reportedProblemPort.reportProblem(reportProblemRequest,
                problemStatus, reportedProblemAddress);

        if (!reportProblemRequest.getImageBase64().isEmpty()) {
            byte[] imageBytes = Base64.getDecoder().decode(reportProblemRequest.getImageBase64());
            final var imageUrl = imageStoragePort.uploadImage(imageBytes, reportedProblemId);
            reportedProblemPort.updateProblemWithImageUrl(imageUrl, reportedProblemId);
        }
        return reportedProblemId;
    }

    @Override
    public Collection<ReportedProblemDto> getNewReportedProblemsByCategories(
            List<String> categories, Integer subscriberId) {
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }
        final var pendingProblems = reportedProblemPort
                .getNewReportedProblemsByCategories(categories, subscriberId);
        final var problems = pendingProblems.stream().peek(
                problem -> {
                    final var rejectedCount = reportedProblemPort
                            .getRejectedProblemsCount(problem.getUserDevice().getDeviceId());
                    problem.setRejectedProblemsCount(rejectedCount);
                }
        ).toList();
        return reportedProblemMapper.toReportedProblemDtos(problems);
    }

    @Override
    public Collection<ReportedProblemDto> getUserReportedProblemsHistory(String userDeviceId) {
        return reportedProblemMapper.toReportedProblemDtos(
                reportedProblemPort.getUserReportedProblemsHistory(userDeviceId)
        );
    }

    @Override
    public Collection<ReportedProblemDto> getSubscriberReportedProblemsHistory(Integer subscriberId) {
        final var problems = reportedProblemPort
                .getSubscriberReportedProblemsHistory(subscriberId)
                .stream()
                .filter(problem ->
                        problem.getProblemStatus().getIdProblemStatus() != SupportedProblemStatus.ACCEPTED.getId())
                .toList();
        return reportedProblemMapper.toReportedProblemDtos(problems);
    }

    @Override
    public ReportedProblemDto updateReportedProblemStatus(Integer reportedProblemId, Integer problemStatusId,
                                                          Integer subscriberId) {
        return reportedProblemMapper.toReportedProblemDto(
                reportedProblemPort.updateReportedProblemStatus(reportedProblemId, problemStatusId, subscriberId)
        );
    }

    @Override
    public void banUserByDeviceId(String userDeviceId) {
        reportedProblemPort.banUserByDeviceId(userDeviceId);
    }
}
