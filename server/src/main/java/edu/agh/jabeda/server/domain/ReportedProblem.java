package edu.agh.jabeda.server.domain;

import java.time.LocalDateTime;

public class ReportedProblem {
    private int idProblemStatus;

    private LocalDateTime reportedDateTime;

    private String description;

    private ReportedProblemAddress reportedProblemAddress;

    private Problem problem;

    private ProblemStatus problemStatus;

    private ReportedProblemSubscriber problemSubscriber;

    private UserDevice userDevice;
}
