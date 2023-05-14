package edu.agh.jabeda.server.adapters.in.web.security.services;

import edu.agh.jabeda.server.application.port.in.AuthUseCase;
import edu.agh.jabeda.server.domain.SubscriberData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final AuthUseCase authUseCase;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SubscriberData user = authUseCase.loadUserByEmail(email);
        return DefaultUserDetails.build(user);
    }
}
