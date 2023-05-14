package edu.agh.jabeda.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportedProblemSubscriber {
    private int idReportedProblemSubscriber;

    private ReportedProblem reportedProblem;

    private Subscriber subscriber;
}
