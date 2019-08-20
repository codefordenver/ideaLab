package idealab.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetAllPrintJobResponse;
import idealab.api.operations.PrintJobOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
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
    public void getAllPrintJobs() throws Exception {
        // given
        GetAllPrintJobResponse printJob = new GetAllPrintJobResponse(1, null, null, null,
                null, null, null, null);
        List<GetAllPrintJobResponse> printJobResponses = new ArrayList<GetAllPrintJobResponse>();
        printJobResponses.add(printJob);
        GetAllPrintJobListResponse expectedResponse = new GetAllPrintJobListResponse(printJobResponses);

        when(printJobOperations.getAllPrintJobs()).thenReturn(expectedResponse);

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
}
