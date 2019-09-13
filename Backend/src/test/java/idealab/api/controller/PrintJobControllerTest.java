package idealab.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetAllPrintJobResponse;
import idealab.api.operations.DropboxOperations;
import idealab.api.operations.PrintJobOperations;
import idealab.api.repositories.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static idealab.api.util.TestUtil.stringToGenericResponse;
import static org.junit.Assert.assertEquals;
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

        Integer printJobId = 3;

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Print Job Updated");
        genericResponse.setHttpStatus(HttpStatus.ACCEPTED);

        String inputJson = printJobRequestAsJsonString(printJobUpdateRequest);

        when(printJobOperations.updatePrintJobStatus(printJobId, printJobUpdateRequest)).thenReturn(genericResponse);

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

        Integer printJobId = 3;

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("Invalid Status");
        genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        String inputJson = printJobRequestAsJsonString(printJobUpdateRequest);

        when(printJobOperations.updatePrintJobStatus(printJobId, printJobUpdateRequest)).thenReturn(genericResponse);

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
        printJobDeleteRequest.setPrintJobId(2);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Print Job Deleted");
        genericResponse.setHttpStatus(HttpStatus.ACCEPTED);

        String inputJson = printJobRequestAsJsonString(printJobDeleteRequest);

        when(printJobOperations.deletePrintJobStatus(printJobDeleteRequest)).thenReturn(genericResponse);

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
        printJobDeleteRequest.setPrintJobId(2);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("Print Job Delete Failed");
        genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        String inputJson = printJobRequestAsJsonString(printJobDeleteRequest);

        when(printJobOperations.deletePrintJobStatus(printJobDeleteRequest)).thenReturn(genericResponse);

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
                MockMvcRequestBuilders.get("/api/printjobs")
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
                MockMvcRequestBuilders.get("/api/printjobs")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getStatus();

        // assert
        assertEquals(expectedResponseStatus, actualResponseStatus);
    }
}
