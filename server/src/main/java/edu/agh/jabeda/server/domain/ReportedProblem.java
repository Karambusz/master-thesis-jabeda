package edu.agh.jabeda.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportedProblem {
    private int idReportedProblem;

    private LocalDateTime reportedDateTime;

    private String description;

    private String imageUrl;

    private ReportedProblemAddress reportedProblemAddress;

    private Problem problem;

    private ProblemStatus problemStatus;

    private ReportedProblemSubscriber problemSubscriber;

    private UserDevice userDevice;

    private Integer rejectedProblemsCount;
}
