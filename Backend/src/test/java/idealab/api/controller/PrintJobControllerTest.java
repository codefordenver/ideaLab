package idealab.api.controller;

import static idealab.api.util.TestUtil.requestAsJsonString;
import static idealab.api.util.TestUtil.stringToGenericResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import idealab.api.dto.response.BasicPrintJob;
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

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.model.ColorType;
import idealab.api.model.CustomerInfo;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.model.Queue;
import idealab.api.model.Status;
import idealab.api.operations.PrintJobOperations;


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

        String inputJson = requestAsJsonString(printJobUpdateRequest);

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

        String inputJson = requestAsJsonString(printJobUpdateRequest);

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

        String inputJson = requestAsJsonString(printJobDeleteRequest);

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

        String inputJson = requestAsJsonString(printJobDeleteRequest);

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

    @Test
    public void getAllPrintJobs() throws Exception {
        // given
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setId(1);

        PrintJob printJob = new PrintJob();

        printJob.setColorType(new ColorType(1, "Red"));
        printJob.setComments("comments");
        printJob.setCreatedAt(LocalDateTime.now());
        printJob.setQueueId(new Queue());
        printJob.setStatus(Status.ARCHIVED);
        printJob.setEmployee(new Employee());
        printJob.setId(1);

        List<PrintJob> printJobList = Arrays.asList(printJob);

        List<BasicPrintJob> basicPrintJobs = new BasicPrintJob().ConvertPrintJobs(printJobList);

        DataResponse<BasicPrintJob> expectedResponse = new DataResponse<BasicPrintJob>();
        expectedResponse.setSuccess(true);
        expectedResponse.setMessage("Successfully returned all print jobs");
        expectedResponse.setData(basicPrintJobs);
        expectedResponse.setHttpStatus(HttpStatus.ACCEPTED);

        Mockito.when(printJobOperations.getAllPrintJobs(null)).thenReturn(expectedResponse);

        // act
        String jsonString = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/print-jobs")
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isAccepted())
        .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        DataResponse<PrintJob> actualResponse = mapper.readValue(jsonString, new TypeReference<DataResponse<PrintJob>>() {});

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
    public void getAllPrintJobsByStatus() throws Exception {
        // given
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setId(1);

        PrintJob printJob = new PrintJob();

        printJob.setColorType(new ColorType(1, "Red"));
        printJob.setComments("comments");
        printJob.setCreatedAt(LocalDateTime.now());
        printJob.setQueueId(new Queue());
        printJob.setStatus(Status.ARCHIVED);
        printJob.setEmployee(new Employee());
        printJob.setCustomerInfo(customerInfo);
        printJob.setId(1);

        List<PrintJob> printJobList = Arrays.asList(printJob);

        List<BasicPrintJob> basicPrintJobs = new BasicPrintJob().ConvertPrintJobs(printJobList);

        DataResponse<BasicPrintJob> expectedResponse = new DataResponse<BasicPrintJob>();
        expectedResponse.setSuccess(true);
        expectedResponse.setMessage("Successfully returned print jobs by " + Status.ARCHIVED.getName() + " status");
        expectedResponse.setData(basicPrintJobs);
        expectedResponse.setHttpStatus(HttpStatus.ACCEPTED);

        Mockito.when(printJobOperations.getAllPrintJobs(Status.ARCHIVED.getName())).thenReturn(expectedResponse);

        // act
        String jsonString = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/print-jobs")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("status", Status.ARCHIVED.getName())
        )
        .andExpect(status().isAccepted())
        .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        DataResponse<PrintJob> actualResponse = mapper.readValue(jsonString, new TypeReference<DataResponse<PrintJob>>() {});

        // assert
        verify(printJobOperations, times(1)).getAllPrintJobs(Status.ARCHIVED.getName());
        assertEquals(actualResponse.getMessage(), expectedResponse.getMessage());
    }

	 @Test
	 public void getDeletablePrintJobs() throws Exception {
	     // given
         CustomerInfo customerInfo = new CustomerInfo();
         customerInfo.setId(1);

		 PrintJob printJob = new PrintJob();
		
		 printJob.setColorType(new ColorType(1, "Red"));
		 printJob.setComments("comments");
		 printJob.setCreatedAt(LocalDateTime.now());
		 printJob.setQueueId(new Queue());
		 printJob.setStatus(Status.PENDING_REVIEW);
		 printJob.setEmployee(new Employee());
		 printJob.setId(1);

         List<PrintJob> printJobList = Arrays.asList(printJob);

         List<BasicPrintJob> basicPrintJobs = new BasicPrintJob().ConvertPrintJobs(printJobList);

         DataResponse<BasicPrintJob> expectedResponse = new DataResponse<BasicPrintJob>();
         expectedResponse.setSuccess(true);
         expectedResponse.setMessage("Successfully returned deletable printjobs.");
         expectedResponse.setData(basicPrintJobs);
         expectedResponse.setHttpStatus(HttpStatus.ACCEPTED);
		
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
		
		 DataResponse<PrintJob> actualResponse = mapper.readValue(jsonString, new TypeReference<DataResponse<PrintJob>>() {});
		
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
