package idealab.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.operations.PrintJobOperations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PrintJobController.class)
public class PrintJobControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PrintJobOperations operations;

    @Test
    public void updatePrintJobStatusSuccess() throws Exception {
        PrintJobUpdateRequest printJobUpdateRequest = new PrintJobUpdateRequest();
        printJobUpdateRequest.setEmployeeId(1);
        printJobUpdateRequest.setPrintStatusId("testId");
        printJobUpdateRequest.setStatus("Completed");

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Print Job Updated");

        String inputJson = printJobUpdateRequestAsJsonString(printJobUpdateRequest);

        when(operations.updatePrintJob(printJobUpdateRequest)).thenReturn(genericResponse);

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
        printJobUpdateRequest.setPrintStatusId("testId");
        printJobUpdateRequest.setStatus("asdfasdfads");

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setMessage("Invalid Status");

        String inputJson = printJobUpdateRequestAsJsonString(printJobUpdateRequest);

        when(operations.updatePrintJob(printJobUpdateRequest)).thenReturn(genericResponse);

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

}
