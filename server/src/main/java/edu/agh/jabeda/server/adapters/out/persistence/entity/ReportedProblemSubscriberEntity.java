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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reportedproblemsubscriber")
public class ReportedProblemSubscriberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreportedproblemsubscriber")
    private int idReportedProblemSubscriber;

    @OneToOne
    @JoinColumn(name = "idreportedproblem")
    private ReportedProblemEntity reportedProblem;

    @ManyToOne
    @JoinColumn(name = "idsubscriber")
    private SubscriberEntity subscriber;
}
