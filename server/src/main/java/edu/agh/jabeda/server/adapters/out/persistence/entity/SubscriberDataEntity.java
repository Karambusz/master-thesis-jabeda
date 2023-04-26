package edu.agh.jabeda.server.adapters.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "subscriberdata")
public class SubscriberDataEntity {

    @Id
    @OneToOne
    @JoinColumn(name = "idsubscriber")
    private SubscriberEntity subscriber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "userrole",
    joinColumns = {@JoinColumn(name = "idsubscriber")},
    inverseJoinColumns = {@JoinColumn(name = "idrole")})
    @JsonIgnore
    private Set<RoleEntity> roles = new HashSet<>();
}
