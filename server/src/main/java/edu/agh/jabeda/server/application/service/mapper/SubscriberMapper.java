package edu.agh.jabeda.server.application.service.mapper;

import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberAddressEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberDataEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberInfoEntity;
import edu.agh.jabeda.server.domain.Subscriber;
import edu.agh.jabeda.server.domain.SubscriberAddress;
import edu.agh.jabeda.server.domain.SubscriberData;
import edu.agh.jabeda.server.domain.SubscriberInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public
interface SubscriberMapper {
    Subscriber toSubscriber(SubscriberEntity entity);
    SubscriberEntity toSubscriberEntity(Subscriber subscriber);
    SubscriberAddress toSubscriberAddress(SubscriberAddressEntity addressEntity);
    SubscriberAddressEntity toSubscriberAddressEntity(SubscriberAddress subscriberAddress);
    SubscriberDataEntity toSubscriberDataEntity(SubscriberData subscriberData);
    SubscriberData toSubscriberData(SubscriberDataEntity dataEntity);
    SubscriberInfo toSubscriberInfo(SubscriberInfoEntity infoEntity);
    SubscriberInfoEntity toSubscriberInfoEntity(SubscriberInfo subscriberInfo);
}
