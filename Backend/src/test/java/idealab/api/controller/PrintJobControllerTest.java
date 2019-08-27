
package idealab.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetAllPrintJobResponse;
import idealab.api.operations.PrintJobOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PrintJobController.class)
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
        printJobUpdateRequest.setPrintStatusId(2);
        printJobUpdateRequest.setStatus("Completed");

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Print Job Updated");

        String inputJson = printJobUpdateRequestAsJsonString(printJobUpdateRequest);

        when(printJobOperations.updatePrintJob(printJobUpdateRequest)).thenReturn(genericResponse);

        ResultActions response = mockMvc.perform(put("/api/printjob/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        MvcResult result = response.andReturn();
        String content = result.getResponse().getContentAsString();
        GenericResponse returnedResponse = stringToGenericResponse(content);
        assert (returnedResponse.equals(genericResponse));
    }

    @Test
    public void updatePrintJobStatusFail() throws Exception {
        PrintJobUpdateRequest printJobUpdateRequest = new PrintJobUpdateRequest();
        printJobUpdateRequest.setEmployeeId(1);
        printJobUpdateRequest.setPrintStatusId(2);
        printJobUpdateRequest.setStatus("asdfasdfads");

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("Invalid Status");

        String inputJson = printJobUpdateRequestAsJsonString(printJobUpdateRequest);

        when(printJobOperations.updatePrintJob(printJobUpdateRequest)).thenReturn(genericResponse);

        ResultActions response = mockMvc.perform(put("/api/printjob/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        MvcResult result = response.andReturn();
        String content = result.getResponse().getContentAsString();
        GenericResponse returnedResponse = stringToGenericResponse(content);
        assert (returnedResponse.equals(genericResponse));
    }

    public String printJobUpdateRequestAsJsonString(PrintJobUpdateRequest obj) {
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

    @Test
    public void getAllPrintJobs() throws Exception {
        // given
        GetAllPrintJobResponse printJob = new GetAllPrintJobResponse(1, null, null, null,
                null, null, null, null);
        List<GetAllPrintJobResponse> printJobResponses = new ArrayList<GetAllPrintJobResponse>();
        printJobResponses.add(printJob);
        GetAllPrintJobListResponse expectedResponse = new GetAllPrintJobListResponse(printJobResponses);

        Mockito.when(printJobOperations.getAllPrintJobs()).thenReturn(expectedResponse);

        // act
        String jsonString = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/printjob")
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        GetAllPrintJobListResponse actualResponse = mapper.readValue(jsonString, GetAllPrintJobListResponse.class);

        int actualId = actualResponse.getPrintJobs().get(0).getId();
        int expectedId = expectedResponse.getPrintJobs().get(0).getId();

        // assert
        assertEquals(expectedId, actualId);
    }

    @Test
    public void getAllPrintJobs_shouldReturn_404_when_thereIsNone() throws Exception {
        // given
        Mockito.when(printJobOperations.getAllPrintJobs()).thenReturn(null);
        int expectedResponseStatus = HttpStatus.BAD_REQUEST.value();

        // act
        int actualResponseStatus = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/printjob")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getStatus();

        // assert
        assertEquals(expectedResponseStatus, actualResponseStatus);
    }
}
