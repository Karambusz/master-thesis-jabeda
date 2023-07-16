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

    @Query("select p from ReportedProblemEntity p where p.problemSubscriber.subscriber.idSubscriber=:idSubscriber and p.problemStatus.idProblemStatus=:statusId")
    Collection<ReportedProblemEntity> getReportedProblemEntitiesBySubscriberAndStatus(
            @Param(value = "idSubscriber") Integer idSubscriber, @Param(value = "statusId") Integer statusId);

    Collection<ReportedProblemEntity> getReportedProblemEntityByUserDevice_DeviceId(String deviseId);

    @Query("select p from ReportedProblemEntity p where p.problemSubscriber.subscriber.idSubscriber=:idSubscriber")
    Collection<ReportedProblemEntity> getReportedProblemEntityBySubscriber(
            @Param(value = "idSubscriber") Integer idSubscriber
    );
    Optional<ReportedProblemEntity> getReportedProblemEntityByIdReportedProblem(Integer id);
}
