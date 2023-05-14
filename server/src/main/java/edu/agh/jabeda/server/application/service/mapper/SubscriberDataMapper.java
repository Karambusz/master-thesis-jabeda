package edu.agh.jabeda.server.application.service.mapper;

import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberDataEntity;
import edu.agh.jabeda.server.domain.SubscriberData;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface SubscriberDataMapper {
    SubscriberData toSubscriberData(SubscriberDataEntity entity);
}
