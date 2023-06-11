package edu.agh.jabeda.server.application.port.in.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ReportedProblemsByCategoriesRequest {
    private final List<Integer> categories;
}
