package idealab.api.operations;

import idealab.api.repositories.EmployeeRepo;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserOperationsTest {

    @InjectMocks
    UserOperations operations;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setup() {
        operations = new UserOperations(employeeRepo, bCryptPasswordEncoder);
    }



}
