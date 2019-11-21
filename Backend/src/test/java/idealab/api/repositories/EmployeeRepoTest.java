package idealab.api.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import idealab.api.repositories.EmployeeRepo;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeRepoTest {
    @Mock
    private EmployeeRepo employeeRepo;
    
    @Test
    /**
     * Make sure the query we created runs and that it doesn't throw
     * an error.
     */
    public void makeSureFindAllEmployeeBasicsWorks() {
        employeeRepo.findAllEmployeeBasics();
    }
}