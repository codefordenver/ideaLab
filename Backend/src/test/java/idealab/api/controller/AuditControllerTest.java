package idealab.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.model.*;
import idealab.api.operations.AuditOperations;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static idealab.api.util.TestUtil.stringToGenericResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@EnableAutoConfiguration
public class AuditControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AuditOperations auditOperations;

    @InjectMocks
    private AuditController controller;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller).build();
    }

    @Test
    public void getAllPrintJobsAuditSuccess() throws Exception {
        String uri = "/api/audit/printjobs";
        // given
        given(auditOperations.allPrintJobsAudit())
                .willReturn(new PrintJobAuditResponse("Fail"));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), new PrintJobAuditResponse());
    }
}