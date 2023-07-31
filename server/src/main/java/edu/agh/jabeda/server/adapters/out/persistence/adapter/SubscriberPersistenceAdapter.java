package edu.agh.jabeda.server.adapters.out.persistence.adapter;

import com.google.maps.model.LatLng;
import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.RoleEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberAddressEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberDataEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberInfoEntity;
import edu.agh.jabeda.server.adapters.out.persistence.repository.CategoryRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.RoleRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.SubscriberDataRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.SubscriberRepository;
import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.application.port.in.model.request.UpdateSubscriberRequest;
import edu.agh.jabeda.server.application.port.out.SubscriberPort;
import edu.agh.jabeda.server.common.PersistenceAdapter;
import edu.agh.jabeda.server.domain.SupportedRole;
import edu.agh.jabeda.server.domain.exception.CategoryNotFoundException;
import edu.agh.jabeda.server.domain.exception.RoleNotFoundException;
import edu.agh.jabeda.server.domain.exception.SubscriberAlreadyExistsException;
import edu.agh.jabeda.server.domain.exception.SubscriberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@PersistenceAdapter
public class SubscriberPersistenceAdapter implements SubscriberPort {
    private final SubscriberRepository subscriberRepository;
    private final SubscriberDataRepository subscriberDataRepository;
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public SubscriberEntity createSubscriber(CreateSubscriberRequest body, LatLng latLng) {
        if (subscriberDataRepository.existsByEmail(body.getEmail())) {
            throw new SubscriberAlreadyExistsException(body.getEmail());
        }
        final var subscriberEntity = new SubscriberEntity();
        subscriberEntity.setFirstName(body.getFirstName());
        subscriberEntity.setLastName(body.getLastName());
        subscriberEntity.setCategories(getCategoriesByName(body.getCategories()));


        final var subscriber = subscriberRepository.save(subscriberEntity);

        final var subscriberDataEntity = createSubscriberDataEntity(body);
        subscriberDataEntity.setIdSubscriber(subscriber.getIdSubscriber());
        subscriber.setSubscriberData(subscriberDataEntity);
        final var subscriberInfoEntity = createSubscriberInfoEntity(body);
        subscriberInfoEntity.setIdSubscriber(subscriber.getIdSubscriber());
        subscriber.setSubscriberInfo(subscriberInfoEntity);

        return subscriberRepository.save(subscriber);
    }

    @Override
    public SubscriberEntity updateSubscriber(UpdateSubscriberRequest body, LatLng latLng) {
        final var authentication  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getOldPassword()));
        if(!authentication.isAuthenticated()){
            throw new SubscriberNotFoundException(body.getEmail());
        }
        final var subscriber = subscriberRepository.findBySubscriberDataEmail(body.getEmail());
        if(subscriber.isEmpty()) {
            throw new SubscriberNotFoundException(body.getEmail());
        }
        final var subscriberEntity = subscriber.get();
        final var data =  subscriberEntity.getSubscriberData();
        final var info =  subscriberEntity.getSubscriberInfo();
        subscriberEntity.setFirstName(body.getFirstName());
        subscriberEntity.setLastName(body.getLastName());
        subscriberEntity.setCategories(getCategoriesByName(body.getCategories()));
        data.setPassword(encoder.encode(body.getNewPassword()));
        info.setPhoneNumber(body.getNumber());

        final var address =  info.getSubscriberAddress();
        address.setCountry(body.getCountry());
        address.setCity(body.getCity());
        address.setStreet(body.getStreet());
        address.setBuildingNumber(body.getBuildingNumber().trim());
        address.setLatitude(latLng.lat);
        address.setLongitude(latLng.lng);

        return subscriberRepository.save(subscriberEntity);

    }

    private SubscriberDataEntity createSubscriberDataEntity(CreateSubscriberRequest body) {
        final var subscriberDataEntity = new SubscriberDataEntity();
        subscriberDataEntity.setEmail(body.getEmail());
        subscriberDataEntity.setPassword(encoder.encode(body.getPassword()));
        subscriberDataEntity.setRoles(getRolesByName(body.getRole()));
        return subscriberDataEntity;
    }

    private SubscriberInfoEntity createSubscriberInfoEntity(CreateSubscriberRequest body) {
        final var subscriberInfoEntity = new SubscriberInfoEntity();
        subscriberInfoEntity.setPhoneNumber(body.getNumber());
        subscriberInfoEntity.setSubscriberAddress(createSubscriberAddressEntity(body));
        return subscriberInfoEntity;
    }

    private SubscriberAddressEntity createSubscriberAddressEntity(CreateSubscriberRequest body) {
        final var subscriberAddressEntity = new SubscriberAddressEntity();
        subscriberAddressEntity.setCountry(body.getCountry());
        subscriberAddressEntity.setCity(body.getCity());
        subscriberAddressEntity.setStreet(body.getStreet());
        final var buildingNumber = body.getBuildingNumber();
        subscriberAddressEntity.setBuildingNumber(buildingNumber != null ? buildingNumber.trim() : null);
        subscriberAddressEntity.setLongitude(19.944544);
        subscriberAddressEntity.setLatitude(50.049683);
        return subscriberAddressEntity;
    }

    private Set<CategoryEntity> getCategoriesByName(Collection<String> categories) {
        if (categories == null) {
            return Collections.emptySet();
        }
        return categories.stream()
                .map(category -> {
                    final var categoryEntity = categoryRepository.findFirstByCategoryName(category);
                    if (categoryEntity.isPresent()) {
                        return categoryEntity.get();
                    } else {
                        throw new CategoryNotFoundException(category);
                    }
                }).collect(Collectors.toSet());
    }

    private Set<RoleEntity> getRolesByName(Collection<String> roles) {
        if (roles == null) {
            RoleEntity userRole = roleRepository.findFirstByRoleName(SupportedRole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException(SupportedRole.ROLE_USER.name()));
            return Set.of(userRole);
        }
        return roles.stream()
                .map(role -> {
                    final var roleEntity = roleRepository.findFirstByRoleName(SupportedRole.valueOf(role));
                    if (roleEntity.isPresent()) {
                        return roleEntity.get();
                    } else {
                        throw new RoleNotFoundException(role);
                    }
                }).collect(Collectors.toSet());
    }


}
