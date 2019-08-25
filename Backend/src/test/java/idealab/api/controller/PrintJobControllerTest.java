package idealab.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobDeleteRequest;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.operations.PrintJobOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@EnableAutoConfiguration
public class PrintJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PrintJobOperations printJobOperations;

    @InjectMocks
    private PrintJobController controller;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller).build();
    }

    @Test
    public void updatePrintJobStatusSuccess() throws Exception {
        PrintJobUpdateRequest printJobUpdateRequest = new PrintJobUpdateRequest();
        printJobUpdateRequest.setEmployeeId(1);
        printJobUpdateRequest.setStatus("Completed");

        Integer printStatusId = 3;

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Print Job Updated");

        String inputJson = printJobRequestAsJsonString(printJobUpdateRequest);

        when(printJobOperations.updatePrintJobStatus(printStatusId, printJobUpdateRequest)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(put("/api/printjobs/3/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }

    @Test
    public void updatePrintJobStatusFail() throws Exception {
        PrintJobUpdateRequest printJobUpdateRequest = new PrintJobUpdateRequest();
        printJobUpdateRequest.setEmployeeId(1);
        printJobUpdateRequest.setStatus("asdfasdfads");

        Integer printStatusId = 3;

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("Invalid Status");

        String inputJson = printJobRequestAsJsonString(printJobUpdateRequest);

        when(printJobOperations.updatePrintJobStatus(printStatusId, printJobUpdateRequest)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(put("/api/printjobs/3/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }

    @Test
    public void deletePrintJobStatusSuccess() throws Exception {
        PrintJobDeleteRequest printJobDeleteRequest = new PrintJobDeleteRequest();
        printJobDeleteRequest.setEmployeeId(1);
        printJobDeleteRequest.setPrintStatusId(2);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Print Job Deleted");

        String inputJson = printJobRequestAsJsonString(printJobDeleteRequest);

        when(printJobOperations.deletePrintJob(printJobDeleteRequest)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(delete("/api/printjobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }

    @Test
    public void deletePrintJobStatusFail() throws Exception {
        PrintJobDeleteRequest printJobDeleteRequest = new PrintJobDeleteRequest();
        printJobDeleteRequest.setEmployeeId(1);
        printJobDeleteRequest.setPrintStatusId(2);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("Print Job Delete Failed");

        String inputJson = printJobRequestAsJsonString(printJobDeleteRequest);

        when(printJobOperations.deletePrintJob(printJobDeleteRequest)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(delete("/api/printjobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }

    public String printJobRequestAsJsonString(Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GenericResponse stringToGenericResponse(String s) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            GenericResponse response = mapper.readValue(s, GenericResponse.class);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}