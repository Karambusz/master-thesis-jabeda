package edu.agh.jabeda.server.application.service.mapper;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryEntity entity);
    CategoryEntity toCategoryEntity(Category category);
}
