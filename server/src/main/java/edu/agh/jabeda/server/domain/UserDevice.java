package edu.agh.jabeda.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDevice {
    private int idUserDevice;

    private String deviceId;

    private LocalDateTime lastTimeReported;

    private boolean isBanned;

    private LocalDateTime banDate;

    private Set<ReportedProblem> reportedProblems = new HashSet<>();
}
