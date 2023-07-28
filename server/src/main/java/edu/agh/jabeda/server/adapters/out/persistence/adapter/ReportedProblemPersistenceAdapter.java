package edu.agh.jabeda.server.adapters.out.persistence.adapter;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemStatusEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemAddressEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemSubscriberEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.UserDeviceEntity;
import edu.agh.jabeda.server.adapters.out.persistence.repository.CategoryRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ProblemStatusRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ReportedProblemAddressRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ReportedProblemRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ReportedProblemSubscriberRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.SubscriberRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.UserDeviceRepository;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.out.ReportedProblemPort;
import edu.agh.jabeda.server.common.PersistenceAdapter;
import edu.agh.jabeda.server.domain.ProblemStatus;
import edu.agh.jabeda.server.domain.ReportedProblemAddress;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import edu.agh.jabeda.server.domain.SupportedProblemStatus;
import edu.agh.jabeda.server.domain.exception.CategoryNotFoundException;
import edu.agh.jabeda.server.domain.exception.ProblemNotFoundException;
import edu.agh.jabeda.server.domain.exception.ReportedProblemNotFoundException;
import edu.agh.jabeda.server.domain.exception.SubscriberNotFoundException;
import edu.agh.jabeda.server.domain.exception.UserBannedException;
import edu.agh.jabeda.server.domain.exception.UserDeviceNotFoundException;
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
    private final SubscriberRepository subscriberRepository;
    private final ReportedProblemSubscriberRepository problemSubscriberRepository;
    private final ReportedProblemAddressRepository reportedProblemAddressRepository;

    @Override
    public ReportedProblemId reportProblem(ReportProblemRequest reportProblemRequest,
                                           ProblemStatus problemStatus,
                                           ReportedProblemAddress reportedProblemAddress) {
        final var reportedProblemEntity = new ReportedProblemEntity();
        reportedProblemEntity.setDescription(reportProblemRequest.getDescription());
        reportedProblemEntity.setReportedDateTime(reportProblemRequest.getDate());
        reportedProblemEntity.setProblemStatus(getProblemStatusEntity(problemStatus.getIdProblemStatus()));
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
    public void updateProblemWithImageUrl(String imageUrl,  ReportedProblemId reportedProblemId) {
        final var reportedProblemEntity = getReportedProblemEntityById(reportedProblemId.id());
        reportedProblemEntity.setImageUrl(imageUrl);
        reportedProblemRepository.save(reportedProblemEntity);
    }

    @Override
    public Collection<ReportedProblemEntity> getNewReportedProblemsByCategories(List<String> categories, Integer subscriberId) {
        final var reportedProblems = new ArrayList<ReportedProblemEntity>();
        categories.forEach(category -> {
            final var categoryEntity = categoryRepository.findFirstByCategoryName(category);
            if(categoryEntity.isPresent()) {
                final var pendingProblemsEntity  =  reportedProblemRepository
                        .getReportedProblemEntitiesByCategoryAndStatus(
                                categoryEntity.get(),
                                SupportedProblemStatus.PENDING.getId()
                        );

                reportedProblems.addAll(pendingProblemsEntity);
            } else {
                throw  new CategoryNotFoundException(String.valueOf(category));
            }
        });

        final var acceptedProblemsEntity = reportedProblemRepository
                .getReportedProblemEntitiesBySubscriberAndStatus(subscriberId,
                        SupportedProblemStatus.ACCEPTED.getId());

        reportedProblems.addAll(acceptedProblemsEntity);
        return reportedProblems;
    }

    public Integer getRejectedProblemsCount(String userDeviceId) {
        return reportedProblemRepository.countRejectedProblemsByDeviceId(userDeviceId);
    }

    @Override
    public Collection<ReportedProblemEntity> getUserReportedProblemsHistory(String userDeviceId) {
        return reportedProblemRepository.getReportedProblemEntityByUserDevice_DeviceId(userDeviceId);
    }

    @Override
    public Collection<ReportedProblemEntity> getSubscriberReportedProblemsHistory(Integer subscriberId) {
        return reportedProblemRepository.getReportedProblemEntityBySubscriberId(subscriberId);
    }

    @Override
    public ReportedProblemEntity updateReportedProblemStatus(Integer reportedProblemId,
           Integer problemStatusId, Integer subscriberId) {
        final var reportedProblemEntity = getReportedProblemEntityById(reportedProblemId);
        final var subscriberEntity = getSubscriberEntityById(subscriberId);
        reportedProblemEntity.setProblemStatus(getProblemStatusEntity(problemStatusId));
        reportedProblemEntity.setProblemSubscriber(
                createReportedProblemSubscriberEntity(reportedProblemEntity, subscriberEntity)
        );
        return reportedProblemRepository.save(reportedProblemEntity);
    }

    @Override
    public void banUserByDeviceId(String userDeviceId) {
        final var userDeviceEntity = getUserDeviceEntity(userDeviceId);
        userDeviceEntity.setBanned(true);
        userDeviceEntity.setBanDate(LocalDateTime.now());
        userDeviceRepository.save(userDeviceEntity);
    }

    private ReportedProblemSubscriberEntity createReportedProblemSubscriberEntity(
            ReportedProblemEntity reportedProblem, SubscriberEntity subscriber) {
        final var reportedProblemSubscriberEntity = new ReportedProblemSubscriberEntity();
        reportedProblemSubscriberEntity.setReportedProblem(reportedProblem);
        reportedProblemSubscriberEntity.setSubscriber(subscriber);
        return problemSubscriberRepository.save(reportedProblemSubscriberEntity);
    }

    private ProblemStatusEntity getProblemStatusEntity(Integer problemStatusId) {
        return problemStatusRepository.getReferenceById(problemStatusId);
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

    private UserDeviceEntity getUserDeviceEntity(String deviceId) {
        final var userDeviceEntity = userDeviceRepository.getUserDeviceEntityByDeviceId(deviceId);

        if (userDeviceEntity.isPresent()) {
            return userDeviceEntity.get();
        }
        throw new UserDeviceNotFoundException(deviceId);
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

    private ReportedProblemEntity getReportedProblemEntityById(Integer reportedProblemId) {
        final var reportedProblemEntity = reportedProblemRepository.getReportedProblemEntityByIdReportedProblem(reportedProblemId);
        if (reportedProblemEntity.isPresent()) {
            return reportedProblemEntity.get();
        }
        throw new ReportedProblemNotFoundException(reportedProblemId);
    }

    private SubscriberEntity getSubscriberEntityById(Integer subscriberId) {
        final var subscriberEntity = subscriberRepository.findById(subscriberId);
        if (subscriberEntity.isPresent()) {
            return subscriberEntity.get();
        }
        throw new SubscriberNotFoundException(subscriberId);
    }
}
