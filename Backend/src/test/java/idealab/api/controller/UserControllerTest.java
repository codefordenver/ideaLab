package idealab.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idealab.api.dto.response.GenericResponse;
import idealab.api.model.Employee;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
