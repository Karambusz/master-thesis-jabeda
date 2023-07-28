package edu.agh.jabeda.server.adapters.out.persistence.adapter;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemStatusEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemSubscriberEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.SubscriberEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.UserDeviceEntity;
import edu.agh.jabeda.server.adapters.out.persistence.repository.CategoryRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ProblemStatusRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ReportedProblemAddressRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ReportedProblemRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ReportedProblemSubscriberRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.SubscriberRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.UserDeviceRepository;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.domain.ProblemStatus;
import edu.agh.jabeda.server.domain.ReportedProblemAddress;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import edu.agh.jabeda.server.domain.SupportedProblemStatus;
import edu.agh.jabeda.server.domain.exception.CategoryNotFoundException;
import edu.agh.jabeda.server.domain.exception.ReportedProblemNotFoundException;
import edu.agh.jabeda.server.domain.exception.UserDeviceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReportedProblemPersistenceAdapterTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Autowired
    private ReportedProblemRepository reportedProblemRepository;

    @Autowired
    private ProblemStatusRepository problemStatusRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private ReportedProblemSubscriberRepository problemSubscriberRepository;

    @Autowired
    private ReportedProblemAddressRepository reportedProblemAddressRepository;

    @Mock
    private PasswordEncoder encoder;

    private ReportedProblemPersistenceAdapter adapter;
    private ProblemStatusEntity problemStatusEntity;
    private ProblemStatusEntity problemStatusEntityAccepted;
    private ProblemStatusEntity problemStatusEntityRejected;
    private UserDeviceEntity userDeviceEntity;
    private SubscriberEntity subscriberEntity;
    private static final ProblemStatus problemStatus = new ProblemStatus(SupportedProblemStatus.PENDING);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new ReportedProblemPersistenceAdapter(userDeviceRepository, reportedProblemRepository,
                problemStatusRepository, categoryRepository, subscriberRepository,
                problemSubscriberRepository, reportedProblemAddressRepository);

        final var categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName("TestCategory");
        final var problemEntity = new ProblemEntity();
        problemEntity.setProblemName("Test Problem");
        categoryEntity.setProblems(Set.of(problemEntity));
        entityManager.persist(categoryEntity);

        problemStatusEntity = new ProblemStatusEntity();
        problemStatusEntity.setStatusCode("Pending");
        problemStatusEntity.setStatusName("Zgłoszono");
        entityManager.persist(problemStatusEntity);
        problemStatusEntityAccepted = new ProblemStatusEntity();
        problemStatusEntityAccepted.setStatusCode("Acceptes");
        problemStatusEntityAccepted.setStatusName("W trakcie");
        entityManager.persist(problemStatusEntityAccepted);
        problemStatusEntityRejected = new ProblemStatusEntity();
        problemStatusEntityRejected.setStatusCode("Rejected");
        problemStatusEntityRejected.setStatusName("Odrzucono");
        entityManager.persist(problemStatusEntityRejected);

        userDeviceEntity = new UserDeviceEntity();
        userDeviceEntity.setDeviceId("device123");
        userDeviceEntity.setLastTimeReported(LocalDateTime.now());
        entityManager.persist(userDeviceEntity);

        subscriberEntity = new SubscriberEntity();
        subscriberEntity.setFirstName("aaa");
        subscriberEntity.setLastName("aaa");
        entityManager.persist(subscriberEntity);
    }

    @Test
    void reportProblem_WithValidRequest_ShouldCreateReportedProblem() {

        final var request = createReportProblemRequest();
        final var address = createReportedProblemAddress();
        final var reportedProblemId = adapter.reportProblem(request, problemStatus, address);

        assertNotNull(reportedProblemId);
        assertNotNull(reportedProblemId.id());

        Optional<ReportedProblemEntity> retrievedReportedProblem = reportedProblemRepository.findById(reportedProblemId.id());
        assertTrue(retrievedReportedProblem.isPresent());
        assertEquals(request.getDescription(), retrievedReportedProblem.get().getDescription());
        assertEquals(request.getDate(), retrievedReportedProblem.get().getReportedDateTime());
        assertNotNull(retrievedReportedProblem.get().getProblemStatus());
        assertEquals(problemStatus.getIdProblemStatus(), retrievedReportedProblem.get().getProblemStatus().getIdProblemStatus());
        assertNotNull(retrievedReportedProblem.get().getProblem());
        assertNotNull(retrievedReportedProblem.get().getUserDevice());
        assertEquals(userDeviceEntity, retrievedReportedProblem.get().getUserDevice());
        assertNotNull(retrievedReportedProblem.get().getReportedProblemAddress());
        assertEquals(address.getAddress(), retrievedReportedProblem.get().getReportedProblemAddress().getAddress());
        assertEquals(address.getLatitude(), retrievedReportedProblem.get().getReportedProblemAddress().getLatitude());
        assertEquals(address.getLongitude(), retrievedReportedProblem.get().getReportedProblemAddress().getLongitude());
    }

    @Test
    void updateProblemWithImageUrl_WithValidRequest_ShouldUpdateImageUrl() {
        final var reportedProblemEntity = new ReportedProblemEntity();
        reportedProblemEntity.setDescription("description");
        final var reportedProblem = entityManager.persist(reportedProblemEntity);

        final var imageUrl = "http://example.com/image.jpg";

        adapter.updateProblemWithImageUrl(imageUrl, new ReportedProblemId(reportedProblem.getIdReportedProblem()));

        Optional<ReportedProblemEntity> updatedReportedProblem = reportedProblemRepository.findById(reportedProblem.getIdReportedProblem());
        assertTrue(updatedReportedProblem.isPresent());
        assertEquals(imageUrl, updatedReportedProblem.get().getImageUrl());
    }

    @Test
    void getNewReportedProblemsByCategories_WithValidCategoriesAndSubscriberId_ShouldReturnReportedProblems() {
        List<String> categories = Arrays.asList("Category1", "Category2");
        Integer subscriberId = 1;

        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setProblemName("Problem");
        CategoryEntity category1Entity = new CategoryEntity();
        category1Entity.setCategoryName("Category1");
        category1Entity.setProblems(Set.of(problemEntity));
        CategoryEntity category2Entity = new CategoryEntity();
        category2Entity.setCategoryName("Category2");
        category2Entity.setProblems(Set.of(problemEntity));
        entityManager.persist(category1Entity);
        entityManager.persist(category2Entity);

        ReportedProblemEntity reportedProblem1 = createReportedProblem(category1Entity);
        ReportedProblemEntity reportedProblem2 = createReportedProblem(category2Entity);

        ReportedProblemSubscriberEntity reportedProblemSubscriberEntity1 = new ReportedProblemSubscriberEntity();
        reportedProblemSubscriberEntity1.setReportedProblem(reportedProblem1);
        reportedProblemSubscriberEntity1.setSubscriber(subscriberEntity);
        entityManager.persist(reportedProblemSubscriberEntity1);

        ReportedProblemSubscriberEntity reportedProblemSubscriberEntity2 = new ReportedProblemSubscriberEntity();
        reportedProblemSubscriberEntity2.setReportedProblem(reportedProblem2);
        reportedProblemSubscriberEntity2.setSubscriber(subscriberEntity);
        entityManager.persist(reportedProblemSubscriberEntity2);

        final var result = adapter.getNewReportedProblemsByCategories(categories, subscriberId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getNewReportedProblemsByCategories_WithUnknownCategory_ShouldThrowCategoryNotFoundException() {
        List<String> categories = Collections.singletonList("UnknownCategory");
        Integer subscriberId = 1;

        assertThrows(CategoryNotFoundException.class, () -> adapter.getNewReportedProblemsByCategories(categories, subscriberId));
    }

    @Test
    void updateProblemStatus_WithValidData_ShouldUpdateStatus() {
        ReportedProblemId reportedProblemId = adapter.reportProblem(createReportProblemRequest(), problemStatus, createReportedProblemAddress());

        ReportedProblemEntity reportedProblemEntity = reportedProblemRepository.findById(reportedProblemId.id())
                .orElseThrow(() -> new IllegalStateException("Reported problem not found"));

        final var updatedReportedProblem = adapter.updateReportedProblemStatus(reportedProblemEntity.getIdReportedProblem(),
                problemStatusEntity.getIdProblemStatus(), userDeviceEntity.getIdUserDevice());

        assertEquals(problemStatusEntity.getIdProblemStatus(), updatedReportedProblem.getProblemStatus().getIdProblemStatus());
    }

    @Test
    void getUserReportedProblemsHistory_WithValidData_ShouldReturnReportedProblems() {

        ReportedProblemId reportedProblemId = adapter.reportProblem(createReportProblemRequest(), problemStatus, createReportedProblemAddress());

        final var userReportedProblems = adapter.getUserReportedProblemsHistory(userDeviceEntity.getDeviceId());

        boolean problemFound = userReportedProblems.stream()
                .anyMatch(problem -> problem.getIdReportedProblem() == reportedProblemId.id());
        assertTrue(problemFound);
    }

    @Test
    void banUserByDeviceId_WithValidData_ShouldBanUser() {
        adapter.reportProblem(createReportProblemRequest(), problemStatus, createReportedProblemAddress());

        adapter.banUserByDeviceId(userDeviceEntity.getDeviceId());

        UserDeviceEntity updatedUserDeviceEntity = entityManager.find(UserDeviceEntity.class, userDeviceEntity.getIdUserDevice());

        assertTrue(updatedUserDeviceEntity.isBanned());
    }

    @Test
    void getRejectedProblemsCount_WithValidData_ShouldReturnCorrectCount() {

        ReportedProblemId reportedProblemId = adapter.reportProblem(createReportProblemRequest(), problemStatus, createReportedProblemAddress());
        ReportedProblemEntity reportedProblemEntity = reportedProblemRepository.findById(reportedProblemId.id())
                .orElseThrow(() -> new IllegalStateException("Reported problem not found"));
        reportedProblemEntity.setProblemStatus(problemStatusEntityRejected);
        entityManager.persist(reportedProblemEntity);

        int rejectedProblemsCount = adapter.getRejectedProblemsCount(userDeviceEntity.getDeviceId());

        assertEquals(1, rejectedProblemsCount);
    }

    @Test
    void getSubscriberReportedProblemsHistory_WithValidData_ShouldReturnReportedProblems() {
        ReportedProblemId reportedProblemId = adapter.reportProblem(createReportProblemRequest(), problemStatus, createReportedProblemAddress());
        ReportedProblemEntity reportedProblemEntity = reportedProblemRepository.findById(reportedProblemId.id())
                .orElseThrow(() -> new IllegalStateException("Reported problem not found"));
        reportedProblemEntity.setProblemStatus(problemStatusEntityRejected);
        entityManager.persist(reportedProblemEntity);

        final var reportedProblemSubscriberEntity = new ReportedProblemSubscriberEntity();
        reportedProblemSubscriberEntity.setReportedProblem(reportedProblemEntity);
        reportedProblemSubscriberEntity.setSubscriber(subscriberEntity);
        entityManager.persist(reportedProblemSubscriberEntity);

        final var subscriberReportedProblems = adapter.getSubscriberReportedProblemsHistory(subscriberEntity.getIdSubscriber());

        boolean problemFound = subscriberReportedProblems.stream()
                .anyMatch(problem -> problem.getIdReportedProblem() == reportedProblemId.id());
        assertTrue(problemFound);
    }

    @Test
    void getNewReportedProblemsByCategories_WithInvalidCategory_ShouldThrowCategoryNotFoundException() {
        List<String> categories = Collections.singletonList("NonExistentCategory");

        assertThrows(CategoryNotFoundException.class, () -> adapter.getNewReportedProblemsByCategories(categories, userDeviceEntity.getIdUserDevice()));
    }

    @Test
    void banUserByDeviceId_WithInvalidUser_ShouldThrowUserDeviceNotFoundException() {
        assertThrows(UserDeviceNotFoundException.class, () -> adapter.banUserByDeviceId("NonExistentUser"));
    }

    @Test
    void updateProblemWithImageUrl_WithInvalidProblemId_ShouldThrowReportedProblemNotFoundException() {
        String imageUrl = "https://example.com/image.png";

        assertThrows(ReportedProblemNotFoundException.class, () -> adapter.updateProblemWithImageUrl(imageUrl, new ReportedProblemId(-1)));
    }

    private ReportedProblemEntity createReportedProblem(CategoryEntity categoryEntity) {
        ReportedProblemEntity reportedProblemEntity = new ReportedProblemEntity();
        reportedProblemEntity.setDescription("Test problem description");
        reportedProblemEntity.setReportedDateTime(LocalDateTime.now());
        reportedProblemEntity.setProblemStatus(problemStatusEntityAccepted);
        reportedProblemEntity.setProblem(categoryEntity.getProblems().stream().findFirst().get());
        entityManager.persist(reportedProblemEntity);
        return reportedProblemEntity;
    }

    private ReportProblemRequest createReportProblemRequest() {
        final var request = new ReportProblemRequest();
        request.setDescription("Test problem description");
        request.setDate(LocalDateTime.now());
        request.setProblem(1);
        request.setCategory(1);
        request.setDeviceId("device123");
        return request;
    }

    private ReportedProblemAddress createReportedProblemAddress() {
        final var address = new ReportedProblemAddress();
        address.setAddress("Test address");
        address.setLatitude(44.7128);
        address.setLongitude(74.0160);
        return address;
    }
}