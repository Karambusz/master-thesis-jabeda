package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<SubscriberEntity, Integer> {

//    @Query("select s from SubscriberEntity s join SubscriberInfoEntity si on s.idSubscriber=si.idSubscriber join SubscriberDataEntity sd on s.idSubscriber=sd.idSubscriber where sd.email=:email")
    Optional<SubscriberEntity> findBySubscriberDataEmail(String email);
}
