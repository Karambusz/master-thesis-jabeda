package edu.agh.jabeda.server.application.port.in.model.response;

import lombok.Getter;

import java.util.List;

@Getter
public class JwtResponse {
    private final String token;
    private final String type = "Bearer";
    private final Integer id;
    private final String username;
    private final String email;
    private final List<String> roles;

    public JwtResponse(String token, Integer id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
