package edu.agh.jabeda.server.application.service;

import edu.agh.jabeda.server.application.port.in.model.usecase.ProblemUseCase;
import edu.agh.jabeda.server.application.port.out.ProblemPort;
import edu.agh.jabeda.server.application.service.mapper.CategoryMapper;
import edu.agh.jabeda.server.common.UseCase;
import edu.agh.jabeda.server.domain.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
@UseCase
@Transactional
public class ProblemService implements ProblemUseCase {
    private final ProblemPort problemPort;
    private final CategoryMapper categoryMapper;
    @Override
    public Collection<Category> getProblems() {
        return problemPort.getProblems()
                .stream()
                .map(categoryMapper::toCategory)
                .toList();
    }
}