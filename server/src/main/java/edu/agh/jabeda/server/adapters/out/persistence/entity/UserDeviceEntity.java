package edu.agh.jabeda.server.adapters.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "userdevice")
public class UserDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduserdevice")
    private int idUserDevice;

    @Column(name = "deviceid")
    private String deviceId;

    @Column(name = "lasttimereported")
    private LocalDateTime lastTimeReported;

    @Column(name = "isbanned")
    private boolean isBanned;

    @Column(name = "bandate")
    private LocalDateTime banDate;

    @OneToMany
    @JoinColumn(name = "iduserdevice")
    @JsonIgnore
    private Set<ReportedProblemEntity> reportedProblems = new HashSet<>();
}
