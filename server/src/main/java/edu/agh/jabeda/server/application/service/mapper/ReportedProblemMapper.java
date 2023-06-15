package edu.agh.jabeda.server.application.service.mapper;

import edu.agh.jabeda.server.adapters.in.web.dto.ProblemStatusDto;
import edu.agh.jabeda.server.adapters.in.web.dto.ReportedProblemDto;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemStatusEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemAddressEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import edu.agh.jabeda.server.domain.ProblemStatus;
import edu.agh.jabeda.server.domain.ReportedProblem;
import edu.agh.jabeda.server.domain.ReportedProblemAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        componentModel = "spring",
        uses = {CategoryMapper.class,
        SubscriberDataMapper.class})
public interface ReportedProblemMapper {
    @Mapping(ignore = true, target = "problemSubscriber")
    @Mapping(ignore = true, target = "userDevice")
    ReportedProblem toReportedProblem(ReportedProblemEntity reportedProblemEntity);
    Collection<ReportedProblem> toReportedProblems(Collection<ReportedProblemEntity> reportedProblemEntities);
    ReportedProblemAddress toReportedProblemAddress(ReportedProblemAddressEntity reportedProblemAddressEntity);
    ProblemStatus toProblemStatus(ProblemStatusEntity problemStatusEntity);
    ProblemStatusDto toProblemStatusDto(ProblemStatus problemStatus);
    ReportedProblemDto toReportedProblemDto(ReportedProblem reportedProblem);
    @Mapping(ignore = true, target = "problemSubscriber")
    @Mapping(ignore = true, target = "userDevice")
    @Mapping(ignore = true, target = "imageBase64")
    Collection<ReportedProblemDto> toReportedProblemDtos(Collection<ReportedProblem> reportedProblems);
}
