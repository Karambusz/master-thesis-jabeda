package edu.agh.jabeda.server.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportedProblemSubscriberDto {

    private int idReportedProblemSubscriber;

    private ReportedProblemDto reportedProblem;

    private SubscriberDto subscriber;
}
