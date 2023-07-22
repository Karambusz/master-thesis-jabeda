package edu.agh.jabeda.server.adapters.in.web.security.services;

import edu.agh.jabeda.server.application.port.in.usecase.AuthUseCase;
import edu.agh.jabeda.server.domain.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultUserDetailsService implements UserDetailsService {
    @Autowired
    private final AuthUseCase authUseCase;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Subscriber user = authUseCase.loadUserByEmail(email);
        return DefaultUserDetails.build(user);
    }
}
