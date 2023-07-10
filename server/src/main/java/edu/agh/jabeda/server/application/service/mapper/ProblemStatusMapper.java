package edu.agh.jabeda.server.application.service.mapper;

import edu.agh.jabeda.server.adapters.in.web.dto.ProblemStatusDto;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface ProblemStatusMapper {
    Collection<ProblemStatusDto> toProblemStatusDto(Collection<ProblemStatusEntity> problemStatusEntity);
}
