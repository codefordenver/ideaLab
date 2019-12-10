package idealab.api.operations;

import idealab.api.dto.response.PrintJobAuditModel;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.service.AuditService;
import org.hibernate.envers.query.AuditQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AuditOperationsTest {
    private AuditOperations operations;

    @Mock
    private EntityManager entityManager;

    @Mock
    private AuditService auditService;

    @Before
    public void setup() {
        operations = new AuditOperations(
                auditService
        );
    }

    @Test
    public void allPrintJobsAuditSuccess() {
        //Given
        PrintJobAuditModel auditModel = new PrintJobAuditModel();
        auditModel.setId(3);
        auditModel.setColor("red");
        auditModel.setEmailHash("no hash here");

        List<PrintJobAuditModel> auditList = new ArrayList<>();
        auditList.add(auditModel);

        AuditQuery query = mock(AuditQuery.class);
        //When
        when(auditService.processPrintJobAuditQuery(any(AuditQuery.class))).thenReturn(auditList);
        when(auditService.getPrintJobAuditQuery()).thenReturn(query);

        PrintJobAuditResponse response = operations.allPrintJobsAudit();

        //then
        PrintJobAuditModel returnedAuditModel = response.getData().get(0);
        assertEquals(response.getData(), auditList);
        assertEquals(returnedAuditModel.getId(), auditModel.getId());
        assertEquals(returnedAuditModel.getColor(), "red");
        assertEquals(returnedAuditModel.getEmailHash(), "no hash here");
    }

    @Test
    public void printJobAuditByIdSuccess() {
        //Given
        PrintJobAuditModel auditModel = new PrintJobAuditModel();
        auditModel.setId(4);
        auditModel.setColor("blue");
        auditModel.setEmailHash("no hash here");

        List<PrintJobAuditModel> auditList = new ArrayList<>();
        auditList.add(auditModel);

        AuditQuery query = mock(AuditQuery.class);
        //When
        when(auditService.processPrintJobAuditQuery(any(AuditQuery.class))).thenReturn(auditList);
        when(auditService.getPrintJobAuditQuery()).thenReturn(query);

        PrintJobAuditResponse response = operations.printJobAuditById(3);

        //then
        PrintJobAuditModel returnedAuditModel = response.getData().get(0);
        assertEquals(response.getData(), auditList);
        assertEquals(returnedAuditModel.getId(), auditModel.getId());
        assertEquals(returnedAuditModel.getColor(), "blue");
        assertEquals(returnedAuditModel.getEmailHash(), "no hash here");
    }
}
