package idealab.api.controller;

import idealab.api.dto.response.PrintJobAuditModel;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.operations.AuditOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static idealab.api.util.TestUtil.stringToPrintJobAuditResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        String uri = "/api/audit/print-jobs";
        // given
        PrintJobAuditModel auditModel = new PrintJobAuditModel();
        auditModel.setId(2);
        auditModel.setColor("red");
        auditModel.setEmailHash("no hash here");

        List<PrintJobAuditModel> auditList = new ArrayList<>();
        auditList.add(auditModel);

        PrintJobAuditResponse auditResponse = new PrintJobAuditResponse(auditList);

        given(auditOperations.allPrintJobsAudit())
                .willReturn(auditResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get(uri)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn().getResponse();


        // then
        PrintJobAuditResponse returnedResponse = stringToPrintJobAuditResponse(response.getContentAsString());
        assertEquals(returnedResponse, auditResponse);
        assertEquals(returnedResponse.getData(), auditList);
    }

    @Test
    public void getPrintJobsAuditByIdSuccess() throws Exception {
        String uri = "/api/audit/print-jobs/3";
        // given
        PrintJobAuditModel auditModel = new PrintJobAuditModel();
        auditModel.setId(3);
        auditModel.setColor("red");
        auditModel.setEmailHash("no hash here");

        List<PrintJobAuditModel> auditList = new ArrayList<>();
        auditList.add(auditModel);

        PrintJobAuditResponse auditResponse = new PrintJobAuditResponse(auditList);

        given(auditOperations.printJobAuditById(3))
                .willReturn(auditResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get(uri)
                        .param("print-id", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn().getResponse();


        // then
        PrintJobAuditResponse returnedResponse = stringToPrintJobAuditResponse(response.getContentAsString());
        assertEquals(returnedResponse, auditResponse);
        assertEquals(returnedResponse.getData(), auditList);
    }
}
