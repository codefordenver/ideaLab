package idealab.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.model.*;
import idealab.api.operations.PrintJobOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
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
    private MockMvc mockMvc;

    @Mock
    private PrintJobOperations printJobOperations;

    @InjectMocks
    private PrintJobController controller;

    @Before
    public void setUp(){
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

        String returnJson = mockMvc.perform(put("/api/print-jobs/3/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
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

        String returnJson = mockMvc.perform(put("/api/print-jobs/3/status")
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

        when(printJobOperations.deletePrintJob(printJobDeleteRequest)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(delete("/api/print-jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
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

        when(printJobOperations.deletePrintJob(printJobDeleteRequest)).thenReturn(genericResponse);

        String returnJson = mockMvc.perform(delete("/api/print-jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        GenericResponse returnedResponse = stringToGenericResponse(returnJson);
        assert (returnedResponse.equals(genericResponse));
    }

    private String printJobRequestAsJsonString(Object obj) {
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
        PrintJob printJob = new PrintJob();

        printJob.setColorTypeId(new ColorType("Red"));
        printJob.setComments("comments");
        printJob.setCreatedAt(LocalDateTime.now());
        printJob.setEmailHashId(new EmailHash());
        printJob.setQueueId(new Queue(1));
        printJob.setStatus(Status.ARCHIVED);
        printJob.setEmployeeId(new Employee());
        printJob.setId(1);

        List<PrintJob> printJobList = Arrays.asList(printJob);

        PrintJobResponse expectedResponse = new PrintJobResponse();
        expectedResponse.setSuccess(true);
        expectedResponse.setMessage("Successfully returned all print jobs");
        expectedResponse.setData(printJobList);
        expectedResponse.setHttpStatus(HttpStatus.ACCEPTED);

        Mockito.when(printJobOperations.getAllPrintJobs()).thenReturn(expectedResponse);

        // act
        String jsonString = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/print-jobs")
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isAccepted())
        .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        PrintJobResponse actualResponse = mapper.readValue(jsonString, PrintJobResponse.class);

        int actualId = actualResponse.getData().get(0)
                .getId();

        int expectedId = expectedResponse
                .getData()
                .get(0)
                .getId();

        // assert
        assertEquals(expectedId, actualId);
    }


     @Test
     public void getDeletablePrintJobs() throws Exception {
         // given
         PrintJob printJob = new PrintJob();

         printJob.setColorTypeId(new ColorType("Red"));
         printJob.setComments("comments");
         printJob.setCreatedAt(LocalDateTime.now());
         printJob.setEmailHashId(new EmailHash());
         printJob.setQueueId(new Queue(1));
         printJob.setStatus(Status.PENDING_REVIEW);
         printJob.setEmployeeId(new Employee());
         printJob.setId(1);

         PrintJobResponse expectedResponse = new PrintJobResponse(printJob);

         Mockito.when(printJobOperations.getDeletablePrintJobs()).thenReturn(expectedResponse);

         // act
         String jsonString = mockMvc.perform(
                 MockMvcRequestBuilders.get("/api/print-jobs/deletable")
                         .accept(MediaType.APPLICATION_JSON)
         )
         .andExpect(status().isAccepted())
         .andReturn().getResponse().getContentAsString();

         ObjectMapper mapper = new ObjectMapper();
         mapper.registerModule(new JavaTimeModule());

         PrintJobResponse actualResponse = mapper.readValue(jsonString, PrintJobResponse.class);

         int actualId = actualResponse.getData()
                 .get(0)
                 .getId();

         int expectedId = expectedResponse
                 .getData()
                 .get(0)
                 .getId();

         // assert
         assertEquals(expectedId, actualId);
     }
}
