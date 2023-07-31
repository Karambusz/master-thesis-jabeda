package edu.agh.jabeda.server.application.service;

import com.google.maps.model.LatLng;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.in.usecase.ReportProblemUseCase;
import edu.agh.jabeda.server.application.port.out.ImageStoragePort;
import edu.agh.jabeda.server.application.port.out.ReportedProblemPort;
import edu.agh.jabeda.server.application.service.mapper.ReportedProblemMapper;
import edu.agh.jabeda.server.common.UseCase;
import edu.agh.jabeda.server.domain.ProblemStatus;
import edu.agh.jabeda.server.domain.ReportedProblem;
import edu.agh.jabeda.server.domain.ReportedProblemAddress;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import edu.agh.jabeda.server.domain.SupportedProblemStatus;
import edu.agh.jabeda.server.domain.exception.UserDeviceNotFoundException;
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

        if (reportProblemRequest.getImageBase64() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(reportProblemRequest.getImageBase64());
            final var imageUrl = imageStoragePort.uploadImage(imageBytes, reportedProblemId);
            reportedProblemPort.updateProblemWithImageUrl(imageUrl, reportedProblemId);
        }
        return reportedProblemId;
    }

    @Override
    public Collection<ReportedProblem> getNewReportedProblemsByCategories(
            List<String> categories, Integer subscriberId) {
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }
        final var pendingProblems = reportedProblemPort
                .getNewReportedProblemsByCategories(categories, subscriberId);

        final var reportedProblems = reportedProblemMapper.toReportedProblems(pendingProblems);
        return reportedProblems.stream().peek(
                problem -> {
                    if (problem.getUserDevice() == null) {
                        throw new UserDeviceNotFoundException();
                    }
                    final var rejectedCount = reportedProblemPort
                            .getRejectedProblemsCount(problem.getUserDevice().getDeviceId());
                    problem.setRejectedProblemsCount(rejectedCount);
                }
        ).toList();
    }

    @Override
    public Collection<ReportedProblem> getUserReportedProblemsHistory(String userDeviceId) {
        return reportedProblemMapper.toReportedProblems(
                reportedProblemPort.getUserReportedProblemsHistory(userDeviceId)
        );
    }

    @Override
    public Collection<ReportedProblem> getSubscriberReportedProblemsHistory(Integer subscriberId) {
        final var problems = reportedProblemPort
                .getSubscriberReportedProblemsHistory(subscriberId)
                .stream()
                .filter(problem ->
                        problem.getProblemStatus().getIdProblemStatus() != SupportedProblemStatus.ACCEPTED.getId())
                .toList();
        return reportedProblemMapper.toReportedProblems(problems);
    }

    @Override
    public ReportedProblem updateReportedProblemStatus(Integer reportedProblemId, Integer problemStatusId,
                                                       Integer subscriberId) {
        return reportedProblemMapper.toReportedProblem(
                reportedProblemPort.updateReportedProblemStatus(reportedProblemId, problemStatusId, subscriberId)
        );
    }

    @Override
    public void banUserByDeviceId(String userDeviceId) {
        reportedProblemPort.banUserByDeviceId(userDeviceId);
    }

    @Override
    public Collection<String> getImagesByCategory(Integer categoryId) {
        return reportedProblemPort.getImagesByCategory(categoryId);
    }
}
