package edu.agh.jabeda.server.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.google.maps.model.LatLng;
import edu.agh.jabeda.server.adapters.out.persistence.adapter.SubscriberPersistenceAdapter;
import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.RoleEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberDataEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.adapters.out.persistence.repository.CategoryRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.RoleRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.SubscriberDataRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.SubscriberRepository;
import edu.agh.jabeda.server.application.port.in.model.request.CreateSubscriberRequest;
import edu.agh.jabeda.server.domain.SupportedRole;
import edu.agh.jabeda.server.domain.exception.CategoryNotFoundException;
import edu.agh.jabeda.server.domain.exception.SubscriberAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class SubscriberPersistenceAdapterTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriberDataRepository subscriberDataRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    private SubscriberPersistenceAdapter adapter;

    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new SubscriberPersistenceAdapter(subscriberRepository, subscriberDataRepository,
                categoryRepository, roleRepository, encoder, authenticationManager);
    }

    @Test
    void createSubscriber_WithValidRequest_ShouldCreateSubscriber() {
        CreateSubscriberRequest request = new CreateSubscriberRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");
        request.setRole(Collections.singleton("ROLE_USER"));
        request.setNumber("123456789");
        request.setCountry("USA");
        request.setCity("New York");
        request.setStreet("123 Main St");
        request.setBuildingNumber("Apt 4B");
        request.setCategories(List.of("category1"));
        LatLng latLng = new LatLng();

        when(encoder.encode(request.getPassword())).thenReturn("encodedPassword");

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName("category1");
        entityManager.persist(categoryEntity);

        RoleEntity userRole = new RoleEntity();
        userRole.setRoleName(SupportedRole.ROLE_USER);
        entityManager.persist(userRole);

        SubscriberEntity createdSubscriber = adapter.createSubscriber(request, latLng);

        assertNotNull(createdSubscriber);
        assertEquals(request.getFirstName(), createdSubscriber.getFirstName());
        assertEquals(request.getLastName(), createdSubscriber.getLastName());

        Optional<SubscriberEntity> retrievedSubscriber = subscriberRepository.findById(createdSubscriber.getIdSubscriber());
        assertTrue(retrievedSubscriber.isPresent());
        assertEquals(createdSubscriber, retrievedSubscriber.get());

        Optional<SubscriberDataEntity> retrievedSubscriberData = subscriberDataRepository.findById(createdSubscriber.getIdSubscriber());
        assertTrue(retrievedSubscriberData.isPresent());
        assertEquals(request.getEmail(), retrievedSubscriberData.get().getEmail());
        assertEquals("encodedPassword", retrievedSubscriberData.get().getPassword());

        assertNotNull(createdSubscriber.getSubscriberInfo());

        assertNotNull(createdSubscriber.getSubscriberInfo().getSubscriberAddress());
        assertEquals(request.getCountry(), createdSubscriber.getSubscriberInfo().getSubscriberAddress().getCountry());
        assertEquals(request.getCity(), createdSubscriber.getSubscriberInfo().getSubscriberAddress().getCity());
        assertEquals(request.getStreet(), createdSubscriber.getSubscriberInfo().getSubscriberAddress().getStreet());
        assertEquals(request.getBuildingNumber(), createdSubscriber.getSubscriberInfo().getSubscriberAddress().getBuildingNumber());
    }

    @Test
    void createSubscriber_WithExistingEmail_ShouldThrowSubscriberAlreadyExistsException() {
        CreateSubscriberRequest request = new CreateSubscriberRequest();
        request.setEmail("existing@example.com");
        LatLng latLng = new LatLng();

        SubscriberDataEntity existingSubscriberData = new SubscriberDataEntity();
        existingSubscriberData.setEmail(request.getEmail());
        entityManager.persist(existingSubscriberData);

        assertThrows(SubscriberAlreadyExistsException.class, () -> adapter.createSubscriber(request, latLng));
    }

    @Test
    void createSubscriber_WithUnknownCategory_ShouldThrowCategoryNotFoundException() {
        CreateSubscriberRequest request = new CreateSubscriberRequest();
        request.setCategories(List.of("unknownCategory"));
        LatLng latLng = new LatLng();

        assertThrows(CategoryNotFoundException.class, () -> adapter.createSubscriber(request, latLng));
    }

    @Test
    void createSubscriber_WithNullRole_ShouldUseDefaultRole() {
        CreateSubscriberRequest request = new CreateSubscriberRequest();
        request.setRole(null);
        LatLng latLng = new LatLng();

        RoleEntity userRole = new RoleEntity();
        userRole.setRoleName(SupportedRole.ROLE_USER);
        entityManager.persist(userRole);

        SubscriberEntity createdSubscriber = adapter.createSubscriber(request, latLng);

        assertNotNull(createdSubscriber);
        assertEquals(request.getFirstName(), createdSubscriber.getFirstName());
        assertEquals(request.getLastName(), createdSubscriber.getLastName());
        assertNotNull(createdSubscriber.getSubscriberData());
        assertEquals(SupportedRole.ROLE_USER, createdSubscriber.getSubscriberData().getRoles().stream().findAny().get().getRoleName());
    }
}