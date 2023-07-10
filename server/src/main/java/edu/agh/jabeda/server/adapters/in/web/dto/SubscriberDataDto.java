package edu.agh.jabeda.server.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SubscriberDataDto {
    private SubscriberDto subscriber;

    private String email;

    @JsonIgnore
    private String password;

    private Set<RoleDto> roles = new HashSet<>();
}
