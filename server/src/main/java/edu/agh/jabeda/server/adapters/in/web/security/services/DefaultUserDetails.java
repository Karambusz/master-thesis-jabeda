package edu.agh.jabeda.server.adapters.in.web.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.agh.jabeda.server.domain.Category;
import edu.agh.jabeda.server.domain.Subscriber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class DefaultUserDetails implements UserDetails {
    @Serial
    private static final long serialVersionUID = 681259033917656345L;
    private final Integer id;
    private final String username;
    private final String email;
    @JsonIgnore
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String number;
    private final String country;
    private final String city;
    private final String street;
    private final String buildingNumber;
    private final Double latitude;
    private final Double longitude;
    private final List<String> categories;
    private final Collection<? extends GrantedAuthority> authorities;

    public static DefaultUserDetails build(Subscriber user) {
        List<GrantedAuthority> authorities = user.getSubscriberData().getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
        final var address = user.getSubscriberInfo().getSubscriberAddress();
        return new DefaultUserDetails(
                user.getIdSubscriber(),
                user.getSubscriberData().getEmail(),
                user.getSubscriberData().getEmail(),
                user.getSubscriberData().getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getSubscriberInfo().getPhoneNumber(),
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getLatitude(),
                address.getLongitude(),
                user.getCategories().stream().map(Category::getCategoryName).toList(),
                authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DefaultUserDetails user = (DefaultUserDetails) o;
        return Objects.equals(id, user.id);
    }
}
