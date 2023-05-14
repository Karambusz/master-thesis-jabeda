package edu.agh.jabeda.server.adapters.out.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "subscriberinfo")
public class SubscriberInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsubscriberinfo")
    private int idSubscriberInfo;

    @OneToOne
    @JoinColumn(name = "idsubscriber")
    private SubscriberEntity subscriber;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "idsubscriberaddress")
    private SubscriberAddressEntity subscriberAddress;

    @Column(name = "phonenumber")
    private String phoneNumber;
}
