package edu.agh.jabeda.server.adapters.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reportedproblem")
public class ReportedProblemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreportedproblem")
    private int idProblemStatus;

    @Column(name = "reporteddatetime")
    private LocalDateTime reportedDateTime;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "reportedproblemaddress")
    private ReportedProblemAddressEntity reportedProblemAddress;

    @ManyToOne
    @JoinColumn(name = "idproblem")
    private ProblemEntity problem;

    @ManyToOne
    @JoinColumn(name = "idproblemstatus")
    private ProblemStatusEntity problemStatus;

    @OneToOne
    @JoinColumn(name = "idreportedproblem")
    private  ReportedProblemSubscriberEntity problemSubscriber;

    @ManyToOne
    @JoinColumn(name = "iduserdevice")
    private UserDeviceEntity userDevice;
}
