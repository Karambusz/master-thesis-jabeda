package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemSubscriberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedProblemSubscriberRepository extends JpaRepository<ReportedProblemSubscriberEntity, Integer>  {
}
