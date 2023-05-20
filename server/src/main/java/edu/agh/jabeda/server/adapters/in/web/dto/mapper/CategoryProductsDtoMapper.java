package edu.agh.jabeda.server.adapters.in.web.dto.mapper;

import edu.agh.jabeda.server.adapters.in.web.dto.CategoryProductsDto;
import edu.agh.jabeda.server.adapters.in.web.dto.ProblemDto;
import edu.agh.jabeda.server.domain.Category;
import edu.agh.jabeda.server.domain.Problem;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryProductsDtoMapper {

    private CategoryProductsDtoMapper() {

    }

    public static CategoryProductsDtoMapper create() {
        return new CategoryProductsDtoMapper();
    }
    public Collection<CategoryProductsDto> mapFromDomain(Collection<Category> categories) {
        return categories.stream()
                .map(this::mapFromDomain).toList();
    }

    public CategoryProductsDto mapFromDomain(Category category) {
        final var categoryProductsDto = new CategoryProductsDto();
        categoryProductsDto.setIdCategory(category.getIdCategory());
        categoryProductsDto.setCategoryName(category.getCategoryName());
        categoryProductsDto.setProblems(mapProblemsFromDomain(category.getProblems()));
        return categoryProductsDto;
    }

    private Set<ProblemDto> mapProblemsFromDomain(Collection<Problem> problems) {
        return problems.stream().map(this::mapFromDomain).collect(Collectors.toSet());
    }

    private ProblemDto mapFromDomain(Problem problem) {
        final var problemDto = new ProblemDto();
        problemDto.setIdProblem(problem.getIdProblem());
        problemDto.setProblemName(problem.getProblemName());
        return  problemDto;
    }
}
