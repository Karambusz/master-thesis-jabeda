package edu.agh.jabeda.server.adapters.out.persistence.repository;

import edu.agh.jabeda.server.adapters.out.persistence.entity.UserDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDeviceEntity, Integer> {

    Optional<UserDeviceEntity> getUserDeviceEntityByDeviceId(String deviceId);
}
