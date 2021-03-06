package idealab.api.operations;

import idealab.api.dto.request.EmployeeSignUpRequest;
import idealab.api.dto.request.EmployeeUpdateRequest;
import idealab.api.dto.request.UserChangePasswordRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.Employee;
import idealab.api.model.EmployeeRole;
import idealab.api.repositories.EmployeeRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertEquals;
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

        EmployeeSignUpRequest request = new EmployeeSignUpRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setRole("ADMIN");
        request.setFirstName("test");
        request.setLastName("lastTest");

        when(bCryptPasswordEncoder.encode(e.getPassword())).thenReturn("password");
        when(employeeRepo.save(e)).thenReturn(e);

        GenericResponse response = operations.userSignUp(request);

        assertTrue(response.isSuccess() == true);
        assertTrue(response.getMessage().equalsIgnoreCase("User Sign Up Successful"));

    }
    
    @Test
    public void userSignUpWithAnExistingUser() {
    	// given
    	Employee e = new Employee();
    	e.setUsername("username");
        e.setPassword("password");

        EmployeeSignUpRequest request = new EmployeeSignUpRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setRole("ADMIN");
        request.setFirstName("test");
        request.setLastName("lastTest");

        when(bCryptPasswordEncoder.encode(e.getPassword())).thenReturn(e.getPassword());
        when(employeeRepo.findEmployeeByUsername(anyString())).thenReturn(e);
  
        // act
        GenericResponse response = operations.userSignUp(request);
        
        // assert
        verify(employeeRepo, times(1)).findEmployeeByUsername(e.getUsername());
        verify(employeeRepo, never()).save(e);
        assertFalse(response.isSuccess());
        assertEquals(response.getMessage(), "User already exists");
        assertEquals(response.getHttpStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = IdeaLabApiException.class) //ValidationException
    public void userSignUpFailValidation() {
        EmployeeSignUpRequest request = new EmployeeSignUpRequest();
        request.setUsername("username");
        request.setPassword(null);
        request.setRole("ADMIN");
        request.setFirstName("test");
        request.setLastName("lastTest");

        operations.userSignUp(request);

    }

    @Test
    public void userSignUpFail() {
        Employee e = new Employee();
        e.setPassword("password");

        EmployeeSignUpRequest request = new EmployeeSignUpRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setRole("ADMIN");
        request.setFirstName("test");
        request.setLastName("lastTest");

        when(bCryptPasswordEncoder.encode(e.getPassword())).thenReturn("password");
        when(employeeRepo.save(any())).thenThrow(new RuntimeException());

        GenericResponse response = operations.userSignUp(request);

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

    @Test
    public void changePasswordSuccess() {
        UserChangePasswordRequest request = new UserChangePasswordRequest();
        request.setUsername("testuser");
        request.setNewPassword("New1");
        request.setConfirmNewPassword("New1");

        Employee e = new Employee();
        e.setUsername("testuser");
        e.setPassword("testpassword");

        when(employeeRepo.findEmployeeByUsername(e.getUsername())).thenReturn(e);
        when(employeeRepo.save(e)).thenReturn(e);

        GenericResponse response = new GenericResponse();
        response.setSuccess(true);
        response.setMessage("Password Changed Successfully");
        response.setHttpStatus(HttpStatus.ACCEPTED);

        GenericResponse opResponse = operations.changePassword(request);

        assert(opResponse.equals(response));
    }

    @Test(expected = IdeaLabApiException.class)
    public void changePasswordInvalidEmployee() {
        UserChangePasswordRequest request = new UserChangePasswordRequest();
        request.setUsername("testuser");
        request.setNewPassword("New1");
        request.setConfirmNewPassword("New1");

        when(employeeRepo.findEmployeeByUsername(anyString())).thenReturn(null);

        operations.changePassword(request);

    }

    @Test(expected = IdeaLabApiException.class)
    public void changePasswordNewPasswordNoMatch() {
        UserChangePasswordRequest request = new UserChangePasswordRequest();
        request.setUsername("testuser");
        request.setNewPassword("New1");
        request.setConfirmNewPassword("New2");

        Employee e = new Employee();
        e.setUsername("testuser");
        e.setPassword("nonmatch");

        when(employeeRepo.findEmployeeByUsername(e.getUsername())).thenReturn(e);

        operations.changePassword(request);
    }

    @Test
    public void userUpdate() {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setUsername("username");
        request.setRole("ADMIN");

        Employee e = new Employee();
        e.setUsername("username");
        e.setRole(EmployeeRole.STAFF);

        when(employeeRepo.findEmployeeByUsername(request.getUsername())).thenReturn(e);

        e.setRole(EmployeeRole.ADMIN);

        when(employeeRepo.save(e)).thenReturn(e);

        GenericResponse response = operations.updateUser(request);

        verify(employeeRepo, times(1)).findEmployeeByUsername(request.getUsername());
        verify(employeeRepo, times(1)).save(e);
        assertTrue(response.isSuccess() == true);
        assertTrue(response.getMessage().equalsIgnoreCase("Employee role updated successfully"));
    }

    @Test(expected = IdeaLabApiException.class)
    public void userUpdate_NoRole() {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setUsername("username");

        GenericResponse response = operations.updateUser(request);

    }

    @Test(expected = IdeaLabApiException.class)
    public void userUpdate_NotValidRole() {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setUsername("username");
        request.setRole("NOT_VALID");

        GenericResponse response = operations.updateUser(request);

    }

    @Test(expected = IdeaLabApiException.class)
    public void userUpdate_NoUsername() {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setRole("ADMIN");

        GenericResponse response = operations.updateUser(request);

    }

    @Test(expected = IdeaLabApiException.class)
    public void userUpdate_NotValidUsername() {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setUsername("username");
        request.setRole("ADMIN");

        Employee e = new Employee();
        e.setUsername("username");
        e.setRole(EmployeeRole.STAFF);

        when(employeeRepo.findEmployeeByUsername(request.getUsername())).thenReturn(null);

        GenericResponse response = operations.updateUser(request);
    }
}
