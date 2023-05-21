package edu.agh.jabeda.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemStatus {
    private int idProblemStatus;

    private String statusName;

    private String statusCode;

    public ProblemStatus(SupportedProblemStatus problemStatus){
        this.idProblemStatus = problemStatus.getId();
        this.statusName = problemStatus.getName();
        this.statusCode = problemStatus.getCode();
    }
}
