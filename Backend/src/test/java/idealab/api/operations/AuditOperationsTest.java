package idealab.api.operations;

import idealab.api.dto.request.*;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.PrintJobAuditModel;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.Queue;
import idealab.api.model.*;
import idealab.api.repositories.*;
import idealab.api.service.AuditUtil;
import idealab.api.service.FileService;
import idealab.api.service.EmailHashUtil;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditQuery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;
import static idealab.api.exception.ErrorType.DROPBOX_UPLOAD_FILE_ERROR;
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
    private AuditUtil auditUtil;

    @Before
    public void setup() {
        operations = new AuditOperations(
                auditUtil
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
        when(auditUtil.processPrintJobAuditQuery(any(AuditQuery.class))).thenReturn(auditList);
        when(auditUtil.getPrintJobAuditQuery()).thenReturn(query);

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
        when(auditUtil.processPrintJobAuditQuery(any(AuditQuery.class))).thenReturn(auditList);
        when(auditUtil.getPrintJobAuditQuery()).thenReturn(query);

        PrintJobAuditResponse response = operations.printJobAuditById(3);

        //then
        PrintJobAuditModel returnedAuditModel = response.getData().get(0);
        assertEquals(response.getData(), auditList);
        assertEquals(returnedAuditModel.getId(), auditModel.getId());
        assertEquals(returnedAuditModel.getColor(), "blue");
        assertEquals(returnedAuditModel.getEmailHash(), "no hash here");
    }
}
