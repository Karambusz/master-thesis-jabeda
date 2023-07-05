package edu.agh.jabeda.server.application.service.mapper;

import edu.agh.jabeda.server.adapters.in.web.dto.ProblemDto;
import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemEntity;
import edu.agh.jabeda.server.domain.BasicCategory;
import edu.agh.jabeda.server.domain.BasicProblem;
import edu.agh.jabeda.server.domain.Category;
import edu.agh.jabeda.server.domain.Problem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryEntity entity);
    Problem toProblem(ProblemEntity problemEntity);
    BasicProblem toBasicProblem(ProblemEntity problemEntity);
    BasicCategory toBasicCategory(CategoryEntity categoryEntity);
    @Mapping(target = "category", source = "category.categoryName")
    ProblemDto toProblemDto(Problem problem);
}
