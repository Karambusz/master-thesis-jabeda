package edu.agh.jabeda.server.adapters.out.persistence.adapter;

import edu.agh.jabeda.server.adapters.out.persistence.entity.CategoryEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ProblemStatusEntity;
import edu.agh.jabeda.server.adapters.out.persistence.repository.CategoryRepository;
import edu.agh.jabeda.server.adapters.out.persistence.repository.ProblemStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collection;


import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProblemPersistenceAdapterTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProblemStatusRepository problemStatusRepository;

    @Autowired
    private TestEntityManager entityManager;


    private ProblemPersistenceAdapter problemPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        problemPersistenceAdapter = new ProblemPersistenceAdapter(categoryRepository,
                problemStatusRepository);
    }

    @Test
    public void getProblems_ShouldReturnAllProblems() {
        CategoryEntity category1 = new CategoryEntity();
        category1.setCategoryName("Category 1");
        entityManager.persist(category1);

        CategoryEntity category2 = new CategoryEntity();
        category2.setCategoryName("Category 2");
        entityManager.persist(category2);

        ProblemStatusEntity status1 = new ProblemStatusEntity();
        status1.setStatusName("Status 1");
        entityManager.persist(status1);

        ProblemStatusEntity status2 = new ProblemStatusEntity();
        status2.setStatusName("Status 2");
        entityManager.persist(status2);

        entityManager.flush();

        Collection<CategoryEntity> result = problemPersistenceAdapter.getProblems();

        assertEquals(2 ,result.size());
    }

    @Test
    public void getProblemStatuses_ShouldReturnAllProblemStatuses() {
        ProblemStatusEntity status1 = new ProblemStatusEntity();
        status1.setStatusName("Status 1");
        entityManager.persist(status1);

        ProblemStatusEntity status2 = new ProblemStatusEntity();
        status2.setStatusName("Status 2");
        entityManager.persist(status2);

        entityManager.flush();

        Collection<ProblemStatusEntity> result = problemPersistenceAdapter.getProblemStatuses();

        assertEquals(2, result.size());
    }
}