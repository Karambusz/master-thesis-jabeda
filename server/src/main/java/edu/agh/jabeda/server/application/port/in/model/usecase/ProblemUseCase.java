package edu.agh.jabeda.server.application.port.in.model.usecase;

import edu.agh.jabeda.server.domain.Category;

import java.util.Collection;

public interface ProblemUseCase {

    Collection<Category> getProblems();
}
