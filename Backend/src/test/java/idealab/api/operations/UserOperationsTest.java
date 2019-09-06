package idealab.api.operations;

import idealab.api.dto.response.GenericResponse;
import idealab.api.model.Employee;
import idealab.api.repositories.EmployeeRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

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

    @Test
    public void userSignUp() {
        Employee e = new Employee();
        e.setPassword("password");

        when(bCryptPasswordEncoder.encode(e.getPassword())).thenReturn("password");
        when(employeeRepo.save(e)).thenReturn(e);

        GenericResponse response = operations.userSignUp(e);

        assertTrue(response.isSuccess() == true);
        assertTrue(response.getMessage().equalsIgnoreCase("User Sign Up Successful"));

    }

    @Test
    public void userSignUpFailNullPassword() {
        Employee e = new Employee();
        e.setPassword(null);

        when(bCryptPasswordEncoder.encode(e.getPassword())).thenThrow(new NullPointerException());

        GenericResponse response = operations.userSignUp(e);

        assertTrue(response.isSuccess() == false);
        assertTrue(response.getMessage().equalsIgnoreCase("User Sign Up Failed"));

    }

    @Test
    public void userSignUpFail() {
        Employee e = new Employee();
        e.setPassword("password");

        when(bCryptPasswordEncoder.encode(e.getPassword())).thenReturn("password");
        when(employeeRepo.save(e)).thenThrow(new RuntimeException());

        GenericResponse response = operations.userSignUp(e);

        assertTrue(response.isSuccess() == false);
        assertTrue(response.getMessage().equalsIgnoreCase("User Sign Up Failed"));

    }

    @Test
    public void userDelete() {
        Employee e = new Employee();
        e.setId(1);

        when(employeeRepo.findEmployeeById(e.getId())).thenReturn(e);
        doNothing().when(employeeRepo).deleteById(e.getId());

        GenericResponse response = operations.deleteUser(e.getId());

        assertTrue(response.isSuccess() == true);
        assertTrue(response.getMessage().equalsIgnoreCase("Employee Deleted Successfully"));
    }

    @Test
    public void userDeleteFailNonEmployee() {
        when(employeeRepo.findEmployeeById(1)).thenReturn(null);

        GenericResponse response = operations.deleteUser(1);

        assertTrue(response.isSuccess() == false);
        assertTrue(response.getMessage().equalsIgnoreCase("Employee ID is not valid"));
    }

    @Test
    public void userDeleteFail() {
        Employee e = new Employee();
        e.setId(1);

        when(employeeRepo.findEmployeeById(e.getId())).thenReturn(e);
        doThrow(new RuntimeException()).when(employeeRepo).deleteById(1);

        GenericResponse response = operations.deleteUser(1);

        assertTrue(response.isSuccess() == false);
        assertTrue(response.getMessage().equalsIgnoreCase("Employee Could Not Be Deleted"));
    }

}
