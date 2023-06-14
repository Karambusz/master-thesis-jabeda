package edu.agh.jabeda.server.adapters.out.persistence.adapter;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemStatusEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemAddressEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.UserDeviceEntity;
import edu.agh.jabeda.server.adapters.out.persistence.repository.CategoryRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ProblemStatusRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ReportedProblemAddressRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ReportedProblemRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.UserDeviceRepository;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.out.ReportedProblemPort;
import edu.agh.jabeda.server.application.service.mapper.ReportedProblemMapper;
import edu.agh.jabeda.server.common.PersistenceAdapter;
import edu.agh.jabeda.server.domain.ProblemStatus;
import edu.agh.jabeda.server.domain.ReportedProblem;
import edu.agh.jabeda.server.domain.ReportedProblemAddress;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import edu.agh.jabeda.server.domain.exception.CategoryNotFoundException;
import edu.agh.jabeda.server.domain.exception.ProblemNotFoundException;
import edu.agh.jabeda.server.domain.exception.UserBannedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@PersistenceAdapter
public class ReportedProblemPersistenceAdapter implements ReportedProblemPort {
    private final UserDeviceRepository userDeviceRepository;
    private final ReportedProblemRepository reportedProblemRepository;
    private final ProblemStatusRepository problemStatusRepository;

    private final CategoryRepository categoryRepository;
    private final ReportedProblemAddressRepository reportedProblemAddressRepository;
    private final ReportedProblemMapper reportedProblemMapper;

    @Override
    public ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest,
                                           ProblemStatus problemStatus,
                                           ReportedProblemAddress reportedProblemAddress) {
        final var reportedProblemEntity = new ReportedProblemEntity();
        reportedProblemEntity.setDescription(reportProblemRequest.getDescription());
        reportedProblemEntity.setReportedDateTime(reportProblemRequest.getDate());
        reportedProblemEntity.setProblemStatus(getProblemStatusEntity(problemStatus));
        final var categoryEntity = getCategory(reportProblemRequest.getCategory());
        final var problemEntity = categoryEntity.getProblems()
                .stream()
                .filter(problem -> problem.getIdProblem() == reportProblemRequest.getProblem())
                .findFirst().orElseThrow(() -> new ProblemNotFoundException(reportProblemRequest.getProblem()));
        reportedProblemEntity.setProblem(problemEntity);

        final var userDeviceEntity = getUserDeviceEntity(reportProblemRequest.getDeviceId(),
                reportProblemRequest.getDate());
        if (userDeviceEntity.isBanned()) {
            throw new UserBannedException(userDeviceEntity.getDeviceId());
        }
        reportedProblemEntity.setUserDevice(userDeviceEntity);

        reportedProblemEntity.setReportedProblemAddress(
                createReportedProblemAddressEntity(reportedProblemAddress)
        );

        final var createdEntity = reportedProblemRepository.save(reportedProblemEntity);

        return new ReportedProblemId(createdEntity.getIdReportedProblem());
    }

    @Override
    public Collection<ReportedProblem> getNewReportedProblemsByCategories(List<Integer> categories) {
        final var reportedProblems = new ArrayList<ReportedProblem>();
        categories.forEach(category -> {
            final var categoryEntity = categoryRepository.findById(category);
            if(categoryEntity.isPresent()) {
                final var problemsEntity  =  reportedProblemRepository
                        .getReportedProblemEntitiesByProblem_Category(categoryEntity.get());
                reportedProblems.addAll(reportedProblemMapper.toReportedProblems(problemsEntity));
            } else {
                throw  new CategoryNotFoundException(String.valueOf(category));
            }
        });
        return reportedProblems;
    }

    private ProblemStatusEntity getProblemStatusEntity(ProblemStatus problemStatus) {
        return problemStatusRepository.getReferenceById(problemStatus.getIdProblemStatus());
    }

    private CategoryEntity getCategory(int categoryId) {
        return categoryRepository.getReferenceById(categoryId);
    }

    private UserDeviceEntity getUserDeviceEntity(String deviceId, LocalDateTime dateTime) {
        final var userDeviceEntity = userDeviceRepository.getUserDeviceEntityByDeviceId(deviceId);

        if (userDeviceEntity.isPresent()) {
            final var deviceEntity = userDeviceEntity.get();
            deviceEntity.setLastTimeReported(dateTime);
            return userDeviceRepository.save(deviceEntity);
        }

        return createUserDeviceEntity(deviceId, dateTime);
    }

    private UserDeviceEntity createUserDeviceEntity(String deviceId, LocalDateTime dateTime) {
        final var userDeviceEntity = new UserDeviceEntity();
        userDeviceEntity.setDeviceId(deviceId);
        userDeviceEntity.setLastTimeReported(dateTime);
        return userDeviceRepository.save(userDeviceEntity);
    }

    private ReportedProblemAddressEntity createReportedProblemAddressEntity(ReportedProblemAddress address) {
        final var addressEntity = new ReportedProblemAddressEntity();
        addressEntity.setAddress(address.getAddress());
        addressEntity.setLatitude(address.getLatitude());
        addressEntity.setLongitude(address.getLongitude());
        return reportedProblemAddressRepository.save(addressEntity);
    }
}
