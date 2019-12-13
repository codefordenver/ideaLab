package idealab.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idealab.api.dto.request.EmployeeUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.model.Employee;
import idealab.api.model.EmployeeRole;
import idealab.api.operations.UserOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static idealab.api.util.TestUtil.stringToGenericResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@EnableAutoConfiguration
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserOperations operations;

    @InjectMocks
    private UserController controller;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller).build();
    }

    @Test
    public void userSignUp() throws Exception {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("User Sign Up Successful");
        genericResponse.setHttpStatus(HttpStatus.CREATED);

        Employee e = new Employee();
        e.setUsername("test");
        e.setPassword("password");

        String inputJson = employeeAsJsonString(e);

        when(operations.userSignUp(any())).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }
    
    @Test
    public void userSignUpWithAnExistingUser() throws Exception {
    	// given
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("User already exists");
        genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        Employee e = new Employee();
        e.setUsername("test");
        e.setPassword("password");

        String inputJson = employeeAsJsonString(e);

        when(operations.userSignUp(any())).thenReturn(genericResponse);

        // act
        String returnJson = mockMvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();
     
        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        
        // assert
        assert (returnedResponse.equals(genericResponse));
    }

    @Test
    public void userSignUpFail() throws Exception {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("User Sign Up Failed");
        genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        Employee e = new Employee();
        e.setUsername("test");
        e.setPassword("password");

        String inputJson = employeeAsJsonString(e);

        when(operations.userSignUp(any())).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }

    @Test
    public void userDelete() throws Exception {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Employee Deleted Successfully");
        genericResponse.setHttpStatus(HttpStatus.ACCEPTED);

        when(operations.deleteUser(1)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }

    @Test
    public void userDeleteFailNonUser() throws Exception {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("Employee ID is not valid");
        genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        when(operations.deleteUser(1)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }

    @Test
    public void userDeleteFail() throws Exception {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("Employee Could Not Be Deleted");
        genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        when(operations.deleteUser(1)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }

    public String employeeAsJsonString(Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void employeeUpdate() throws Exception {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Employee Updated Successful");
        genericResponse.setHttpStatus(HttpStatus.ACCEPTED);

        EmployeeUpdateRequest e = new EmployeeUpdateRequest();
        e.setUsername("test");
        e.setRole("ADMIN");

        String inputJson = employeeAsJsonString(e);

        when(operations.updateUser(e)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(put("/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);

        verify(operations, times(1)).updateUser(e);
        assert (returnedResponse.equals(genericResponse));
    }

    @Test
    public void employeeUpdate_UpdateFailed() throws Exception {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("Employee  Successful");
        genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        EmployeeUpdateRequest e = new EmployeeUpdateRequest();
        e.setUsername("test");
        e.setRole("ADMIN");

        String inputJson = employeeAsJsonString(e);

        when(operations.updateUser(e)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(put("/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);

        // assert
        verify(operations, times(1)).updateUser(e);
        assert (returnedResponse.equals(genericResponse));
    }
}
