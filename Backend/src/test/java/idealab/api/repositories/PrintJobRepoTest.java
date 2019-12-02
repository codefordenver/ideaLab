package idealab.api.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class PrintJobRepoTest {
    @Mock
    private PrintJobRepo printJobRepo;

    @Test
    /**
     * This test ensures that nothing has changed with the query.
     */
    public void checkFindActive() {
        printJobRepo.findActive();
    }

}
