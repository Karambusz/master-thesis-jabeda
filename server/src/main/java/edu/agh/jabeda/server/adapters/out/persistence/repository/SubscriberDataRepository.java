package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberDataRepository extends JpaRepository<SubscriberDataEntity, Integer> {
    Optional<SubscriberDataEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
}
