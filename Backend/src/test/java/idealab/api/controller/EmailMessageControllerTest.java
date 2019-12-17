package idealab.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import idealab.api.dto.request.EmailMessageUpdateRequest;
import idealab.api.dto.response.EmailMessageResponse;
import idealab.api.model.EmailMessage;
import idealab.api.model.Status;
import idealab.api.operations.EmailMessageOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static idealab.api.util.TestUtil.requestAsJsonString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@EnableAutoConfiguration
public class EmailMessageControllerTest {
    private MockMvc mockMvc;

    @Mock
    private EmailMessageOperations operations;

    @InjectMocks
    private EmailMessageController controller;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller).build();
    }

    @Test
    public void getMessageByStatusController() throws Exception {
        // given
        EmailMessage emailMessage = new EmailMessage(Status.fromValue("COMPLETED"), "EMAIL MESSAGE RESPONSE");

        EmailMessageResponse expectedResponse = new EmailMessageResponse(emailMessage);

        Mockito.when(operations.getMessageByStatus(any())).thenReturn(expectedResponse);

        // act
        String jsonString = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/message")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("status", "COMPLETED")
        )
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        EmailMessageResponse actualResponse = mapper.readValue(jsonString, new TypeReference<EmailMessageResponse>() {
        });

        // assert
        verify(operations, times(1)).getMessageByStatus(any());
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void updateMessageController() throws Exception {
        // given
        EmailMessageUpdateRequest request = new EmailMessageUpdateRequest();
        request.setStatus("COMPLETED");
        request.setEmailMessage("EMAIL MESSAGE RESPONSE");
        String inputJson = requestAsJsonString(request);

        EmailMessage emailMessage = new EmailMessage(Status.fromValue("COMPLETED"), "EMAIL MESSAGE RESPONSE");

        EmailMessageResponse expectedResponse = new EmailMessageResponse(emailMessage);

        Mockito.when(operations.updateMessage(any())).thenReturn(expectedResponse);

        // act
        String jsonString = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        EmailMessageResponse actualResponse = mapper.readValue(jsonString, new TypeReference<EmailMessageResponse>() {
        });

        // assert
        verify(operations, times(1)).updateMessage(any());
        assertEquals(actualResponse, expectedResponse);
    }
}