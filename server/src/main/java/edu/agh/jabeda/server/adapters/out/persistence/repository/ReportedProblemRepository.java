package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedProblemRepository extends JpaRepository<ReportedProblemEntity, Integer> {
}
