package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface ReportedProblemRepository extends JpaRepository<ReportedProblemEntity, Integer> {
    @Query("select p from ReportedProblemEntity p where p.problem.category=:category and p.problemStatus.idProblemStatus=:statusId")
    Collection<ReportedProblemEntity> getReportedProblemEntitiesByCategoryAndStatus(
            @Param(value = "category") CategoryEntity category, @Param(value = "statusId") Integer statusId);

    @Query("select p from ReportedProblemEntity p join ReportedProblemSubscriberEntity ps on p.idReportedProblem = ps.reportedProblem.idReportedProblem where ps.subscriber.idSubscriber=:idSubscriber and p.problemStatus.idProblemStatus=:statusId")
    Collection<ReportedProblemEntity> getReportedProblemEntitiesBySubscriberAndStatus(
            @Param(value = "idSubscriber") Integer idSubscriber, @Param(value = "statusId") Integer statusId);

    @Query("select count(p) from ReportedProblemEntity p join UserDeviceEntity d on p.userDevice.idUserDevice = d.idUserDevice where  d.deviceId=:deviceId and p.problemStatus.idProblemStatus=3")
    Integer countRejectedProblemsByDeviceId(@Param(value = "deviceId") String deviceId);

    Collection<ReportedProblemEntity> getReportedProblemEntityByUserDevice_DeviceId(String deviseId);

    @Query("select p from ReportedProblemEntity p join ReportedProblemSubscriberEntity ps on p.idReportedProblem = ps.reportedProblem.idReportedProblem where ps.subscriber.idSubscriber=:idSubscriber")
    Collection<ReportedProblemEntity> getReportedProblemEntityBySubscriberId(
            @Param(value = "idSubscriber") Integer idSubscriber
    );
    Optional<ReportedProblemEntity> getReportedProblemEntityByIdReportedProblem(Integer id);

    @Query("select p.imageUrl from ReportedProblemEntity p where p.problem.category.idCategory=:categoryId")
    Collection<String> getImageUrlsByProblemCategory(@Param(value = "categoryId") Integer categoryId);
}
