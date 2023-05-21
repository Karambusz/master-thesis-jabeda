package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedProblemAddressRepository extends JpaRepository<ReportedProblemAddressEntity, Integer> {
}
