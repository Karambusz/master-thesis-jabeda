package edu.agh.jabeda.server.application.port.in.usecase;

import edu.agh.jabeda.server.adapters.in.web.dto.ProblemStatusDto;
import edu.agh.jabeda.server.domain.Category;

import java.util.Collection;

public interface ProblemUseCase {

    Collection<Category> getProblems();

    Collection<ProblemStatusDto> getProblemStatuses();
}
