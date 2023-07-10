package edu.agh.jabeda.server.application.port.out;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemStatusEntity;

import java.util.Collection;

public interface ProblemPort {
    Collection<CategoryEntity> getProblems();

    Collection<ProblemStatusEntity> getProblemStatuses();
}
