package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemStatusRepository extends JpaRepository<ProblemStatusEntity, Integer> {
}
