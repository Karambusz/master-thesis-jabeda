package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReportedProblemRepository extends JpaRepository<ReportedProblemEntity, Integer> {

    Collection<ReportedProblemEntity> getReportedProblemEntitiesByProblem_Category(CategoryEntity category);
}
