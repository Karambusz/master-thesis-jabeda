package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ReportedProblemRepository extends JpaRepository<ReportedProblemEntity, Integer> {
    Collection<ReportedProblemEntity> getReportedProblemEntitiesByProblem_Category(CategoryEntity category);
    Optional<ReportedProblemEntity> getReportedProblemEntityByIdReportedProblem(Integer id);
}
