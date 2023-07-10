package edu.agh.jabeda.server.application.service.mapper;

import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberAddressEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.domain.Subscriber;
import edu.agh.jabeda.server.domain.SubscriberAddress;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        componentModel = "spring",
        uses = {CategoryMapper.class,
                SubscriberDataMapper.class})
public interface SubscriberMapper {
    Subscriber toSubscriber(SubscriberEntity entity);
    SubscriberAddress toSubscriberAddress(SubscriberAddressEntity addressEntity);
}
