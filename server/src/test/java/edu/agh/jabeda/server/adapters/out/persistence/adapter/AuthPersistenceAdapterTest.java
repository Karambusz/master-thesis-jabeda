package edu.agh.jabeda.server.adapters.out.persistence.adapter;

import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberDataEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.adapters.out.persistence.repository.SubscriberRepository;
import edu.agh.jabeda.server.application.service.mapper.CategoryMapper;
import edu.agh.jabeda.server.application.service.mapper.CategoryMapperImpl;
import edu.agh.jabeda.server.application.service.mapper.SubscriberDataMapper;
import edu.agh.jabeda.server.application.service.mapper.SubscriberDataMapperImpl;
import edu.agh.jabeda.server.application.service.mapper.SubscriberMapper;
import edu.agh.jabeda.server.application.service.mapper.SubscriberMapperImpl;
import edu.agh.jabeda.server.domain.Subscriber;
import edu.agh.jabeda.server.domain.exception.SubscriberNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({SubscriberMapperImpl.class,
        CategoryMapperImpl.class,
        SubscriberDataMapperImpl.class})
public class AuthPersistenceAdapterTest {

    @Autowired
    private SubscriberRepository subscriberRepository;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private SubscriberMapper subscriberMapper;
    private AuthPersistenceAdapter authPersistenceAdapter;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SubscriberDataMapper subscriberDataMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authPersistenceAdapter = new AuthPersistenceAdapter(subscriberRepository,
                subscriberMapper);
    }

    @Test
    public void loadUserByEmail_WithValidEmail_ShouldReturnSubscriber() {
        SubscriberEntity subscriberEntity = new SubscriberEntity();
        subscriberEntity.setFirstName("John");
        subscriberEntity.setLastName("Doe");
        final var subscriberData = new SubscriberDataEntity();
        subscriberData.setIdSubscriber(1);
        subscriberData.setEmail("john.doe@example.com");
        entityManager.persist(subscriberData);
        subscriberEntity.setSubscriberData(subscriberData);
        entityManager.persist(subscriberEntity);

        Subscriber result = authPersistenceAdapter.loadUserByEmail("john.doe@example.com");

        assertNotNull(result);
        assertEquals(result.getFirstName(), subscriberEntity.getFirstName());
        assertEquals(result.getLastName(), subscriberEntity.getLastName());
        assertEquals(result.getSubscriberData().getEmail(),  subscriberEntity.getSubscriberData().getEmail());
    }

    @Test
    public void loadUserByEmail_WithInvalidEmail_ShouldThrowSubscriberNotFoundException() {
        assertThrows(SubscriberNotFoundException.class,
                () -> authPersistenceAdapter.loadUserByEmail("nonexistent@example.com"));
    }
}