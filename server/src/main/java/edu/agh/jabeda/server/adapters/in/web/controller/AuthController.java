package edu.agh.jabeda.server.adapters.in.web.controller;

import edu.agh.jabeda.server.adapters.in.web.security.jwt.JwtUtils;
import edu.agh.jabeda.server.adapters.in.web.security.services.DefaultUserDetails;
import edu.agh.jabeda.server.application.port.in.model.usecase.SubscriberUseCase;
import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.application.port.in.model.response.JwtResponse;
import edu.agh.jabeda.server.application.port.in.model.request.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


//@Tag(name = "Auth API", description = "Methods for user authentication")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final SubscriberUseCase subscriberUseCase;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseEntity<JwtResponse> authenticateUser( @RequestBody LoginRequest loginRequest) {
        encoder.encode("password");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateSubscriberRequest signUpRequest) {
        return ResponseEntity.ok(subscriberUseCase.createSubscriber(signUpRequest));
    }
}
