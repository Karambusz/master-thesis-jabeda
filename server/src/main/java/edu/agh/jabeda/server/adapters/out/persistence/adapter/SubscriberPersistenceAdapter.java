package edu.agh.jabeda.server.adapters.out.persistence.adapter;

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
import edu.agh.jabeda.server.application.port.out.SubscriberPort;
import edu.agh.jabeda.server.common.PersistenceAdapter;
import edu.agh.jabeda.server.domain.Subscriber;
import edu.agh.jabeda.server.domain.SupportedRole;
import edu.agh.jabeda.server.domain.exception.CategoryNotFoundException;
import edu.agh.jabeda.server.domain.exception.RoleNotFoundException;
import edu.agh.jabeda.server.domain.exception.SubscriberAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
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

    @Override
    public Collection<Subscriber> getSubscribers() {
        return null;
    }

    @Override
    public SubscriberEntity createSubscriber(CreateSubscriberRequest body) {
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
        subscriberInfoEntity.setSubscriber(subscriber);
        subscriber.setSubscriberInfo(subscriberInfoEntity);

        return subscriberRepository.save(subscriber);
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
        subscriberAddressEntity.setBuildingNumber(body.getBuildingNumber());
        subscriberAddressEntity.setLongitude(19.944544);
        subscriberAddressEntity.setLatitude(50.049683);
        return subscriberAddressEntity;
    }

    private Set<CategoryEntity> getCategoriesByName(Collection<String> categories) {
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
