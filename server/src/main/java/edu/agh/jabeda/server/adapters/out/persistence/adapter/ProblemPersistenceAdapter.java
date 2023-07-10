package edu.agh.jabeda.server.adapters.out.persistence.adapter;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemStatusEntity;
import edu.agh.jabeda.server.adapters.out.persistence.repository.CategoryRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ProblemStatusRepository;
import edu.agh.jabeda.server.application.port.out.ProblemPort;
import edu.agh.jabeda.server.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@PersistenceAdapter
public class ProblemPersistenceAdapter implements ProblemPort {

    private final CategoryRepository categoryRepository;
    private final ProblemStatusRepository problemStatusRepository;
    @Override
    public Collection<CategoryEntity> getProblems() {
        log.info("Loading all problems with categories...");
        return categoryRepository.findAll();
    }

    @Override
    public Collection<ProblemStatusEntity> getProblemStatuses() {
        log.info("Loading all problem statuses...");
        return problemStatusRepository.findAll();
    }
}
