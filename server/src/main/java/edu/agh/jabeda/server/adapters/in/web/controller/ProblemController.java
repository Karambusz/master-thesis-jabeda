package edu.agh.jabeda.server.adapters.in.web.controller;

import edu.agh.jabeda.server.adapters.in.web.dto.mapper.CategoryProductsDtoMapper;
import edu.agh.jabeda.server.application.port.in.model.usecase.ProblemUseCase;
import edu.agh.jabeda.server.adapters.in.web.dto.CategoryProductsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/problems")
public class ProblemController {
    private final ProblemUseCase problemUseCase;

    @GetMapping
    Collection<CategoryProductsDto> getProblems() {
        return CategoryProductsDtoMapper.create()
                .mapFromDomain(problemUseCase.getProblems());
    }
}
