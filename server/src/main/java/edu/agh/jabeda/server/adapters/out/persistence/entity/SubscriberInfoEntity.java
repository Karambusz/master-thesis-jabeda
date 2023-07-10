package edu.agh.jabeda.server.adapters.out.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "idsubscriber")
    private int idSubscriber;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "idsubscriberaddress")
    private SubscriberAddressEntity subscriberAddress;

    @Column(name = "phonenumber")
    private String phoneNumber;
}
