package edu.agh.jabeda.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
