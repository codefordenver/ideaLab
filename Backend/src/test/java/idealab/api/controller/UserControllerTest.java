//package idealab.api.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import idealab.api.dto.GenericResponse;
//import idealab.api.model.Employee;
//import idealab.api.operations.UserOperations;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static idealab.api.util.TestUtil.stringToGenericResponse;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(MockitoJUnitRunner.class)
//@EnableAutoConfiguration
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserOperations operations;
//
//    @InjectMocks
//    private UserController controller;
//
//    @Before
//    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(controller).build();
//    }
//
//    @Test
//    public void userSignUp() {
//        GenericResponse genericResponse = new GenericResponse();
//        genericResponse.setSuccess(true);
//        genericResponse.setMessage("Print Job Updated");
//
//        Employee e = new Employee();
//
//        String inputJson = employeeAsJsonString()
//
//        String returnJson = mockMvc.perform(put("/api/printjobs/3/status")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(inputJson)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is2xxSuccessful())
//                .andReturn().getResponse().getContentAsString();
//
//        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
//        assert (returnedResponse.equals(genericResponse));
//    }
//
//    public String employeeAsJsonString(Object obj) {
//        try {
//            final ObjectMapper mapper = new ObjectMapper();
//            return mapper.writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
