package edu.agh.jabeda.server.adapters.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriber")
public class SubscriberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsubscriber")
    private int idSubscriber;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @OneToOne
    @JoinColumn(name = "idsubscriber")
    private SubscriberDataEntity subscriberData;

    @OneToOne
    @JoinColumn(name = "idsubscriber")
    private SubscriberInfoEntity subscriberInfo;

    @ManyToMany
    @JoinTable(name = "subcribercategory",
    joinColumns = {@JoinColumn(name = "idsubscriber")},
    inverseJoinColumns = {@JoinColumn(name = "idcategory")})
    private Set<CategoryEntity> categories = new HashSet<>();
}
