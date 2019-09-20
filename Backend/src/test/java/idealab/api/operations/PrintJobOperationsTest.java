package idealab.api.operations;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetPrintJobResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.*;
import idealab.api.repositories.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class PrintJobOperationsTest {
    private PrintJobOperations operations;

    @Mock
    PrintJobRepo printJobRepo;

    @Mock
    private DropboxOperations dropboxOperations;

    @Mock
    private ColorTypeRepo colorTypeRepo;

    @Mock
    private EmailHashRepo emailHashRepo;

    @Mock
    private CustomerInfoRepo customerInfoRepo;

    @Mock
    private EmployeeRepo employeeRepo;

    @Before
    public void setup() {
        operations = new PrintJobOperations(
                dropboxOperations,
                printJobRepo,
                colorTypeRepo,
                emailHashRepo,
                customerInfoRepo,
                employeeRepo
        );
    }

    @Test
    public void updatePrintJobSuccess() {
        //Given
        PrintJobUpdateRequest request = new PrintJobUpdateRequest();
        request.setEmployeeId(1);
        request.setStatus("Completed");

        Employee employee = new Employee();
        employee.setId(1);

        PrintJob printJob = new PrintJob();
        printJob.setEmployeeId(employee);
        printJob.setId(2);
        printJob.setStatus(Status.COMPLETED);

        //When
        when(employeeRepo.findEmployeeById(anyInt())).thenReturn(employee);
        when(printJobRepo.findPrintJobById(anyInt())).thenReturn(printJob);
        when(printJobRepo.save(printJob)).thenReturn(printJob);

        GenericResponse response = operations.updatePrintJobStatus(2, request);

        //Then
        assertTrue("response is not true", response.isSuccess() == true);
        assertTrue("print job was not updated", response.getMessage().equalsIgnoreCase("Print Job Updated"));
    }

    @Test(expected = IdeaLabApiException.class)
    public void updatePrintJob_shouldThrow_IdeaLabApiException_when_requestStatus_is_invalid() {
        //Given
        PrintJobUpdateRequest request = new PrintJobUpdateRequest();
        request.setEmployeeId(1);
        request.setStatus("adfasfasf");
        request.setStatus("invalidStatus");

        Employee employee = new Employee();
        employee.setId(1);

        PrintJob printJob = new PrintJob();
        printJob.setEmployeeId(employee);
        printJob.setId(2);
        printJob.setStatus(Status.COMPLETED);

        //When
        when(employeeRepo.findEmployeeById(anyInt())).thenReturn(employee);
        when(printJobRepo.findPrintJobById(anyInt())).thenReturn(printJob);

        operations.updatePrintJobStatus(2, request);
    }

    @Test
    public void deletePrintJobSuccess() {
        PrintJobDeleteRequest request = new PrintJobDeleteRequest();
        request.setEmployeeId(1);
        request.setPrintJobId(2);

        Employee employee = new Employee();
        employee.setId(1);

        PrintJob printJob = new PrintJob();
        printJob.setEmployeeId(employee);
        printJob.setId(2);

        when(employeeRepo.findEmployeeById(anyInt())).thenReturn(employee);
        when(printJobRepo.findPrintJobById(anyInt())).thenReturn(printJob);
        doNothing().when(printJobRepo).delete(printJob);

        GenericResponse response = operations.deletePrintJob(request);

        assertTrue("response is not true", response.isSuccess() == true);
        assertTrue("print job was not deleted", response.getMessage().equalsIgnoreCase("Print Job Deleted Successfully"));
    }

    @Test(expected = IdeaLabApiException.class)
    public void deletePrintJob_shouldThrow_IdeaLabApiException_when_employee_is_not_exist() {
        // given
        PrintJobDeleteRequest request = new PrintJobDeleteRequest();
        request.setEmployeeId(1);
        request.setPrintJobId(2);

        Employee employee = new Employee();
        employee.setId(1);

        PrintJob printJob = new PrintJob();
        printJob.setEmployeeId(employee);
        printJob.setId(2);

        when(employeeRepo.findEmployeeById(anyInt())).thenReturn(null);
        when(printJobRepo.findPrintJobById(anyInt())).thenReturn(printJob);

        // when
        operations.deletePrintJob(request);
        GenericResponse response = operations.deletePrintJob(request);

        assertTrue("response is not false", response.isSuccess() == false);
        assertTrue("print job was deleted", response.getMessage().equalsIgnoreCase("Print Job Delete Failed"));
    }

    @Test(expected = IdeaLabApiException.class)
    public void deletePrintJob_shouldThrow_IdeaLabApiException_when_printJob_is_not_exist() {
        PrintJobDeleteRequest request = new PrintJobDeleteRequest();
        request.setEmployeeId(1);
        request.setPrintJobId(2);

        Employee employee = new Employee();
        employee.setId(1);

        when(employeeRepo.findEmployeeById(anyInt())).thenReturn(employee);
        when(printJobRepo.findPrintJobById(anyInt())).thenReturn(null);

        operations.deletePrintJob(request);
    }

    @Test
    public void getAllPrintJobs(){
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

        List<PrintJob> printJobs = new ArrayList<PrintJob>();
        printJobs.add(printJob);

        when(printJobRepo.findAll()).thenReturn(printJobs);

        // when
        GetPrintJobResponse result = operations.getAllPrintJobs();

        // assert
        Assert.assertEquals(result.getData().get(0).getId(), printJob.getId());
    }

    @Test(expected = IdeaLabApiException.class)
    public void getAllPrintJobs_shouldThrow_IdeaLabApiException_when_printJob_is_not_exist(){
        // given
        when(printJobRepo.findAll()).thenReturn(null);

        // when
        operations.getAllPrintJobs();
    }

    @Test(expected = IdeaLabApiException.class)
    public void getAllPrintJobs_shouldThrow_IdeaLabApiException_when_empty_printJobs_returned(){
        // given
        when(printJobRepo.findAll()).thenReturn(new ArrayList<>());

        // when
        operations.getAllPrintJobs();

        // when
        operations.getAllPrintJobs();
    }

    @Test
    public void getDeletablePrintJobs(){
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

        List<PrintJob> printJobs = new ArrayList<PrintJob>();
        printJobs.add(printJob);
        List<Status> deletableStatuses = Arrays.asList(new Status[]{
            Status.PENDING_REVIEW,
            Status.FAILED,
            Status.PENDING_CUSTOMER_RESPONSE,
            Status.REJECTED,
            Status.COMPLETED,
            Status.ARCHIVED
        });
        when(printJobRepo.findByStatusIn(deletableStatuses)).thenReturn(printJobs);

        // when
        GetAllPrintJobListResponse result = operations.getDeletablePrintJobs();

        // assert
        Assert.assertEquals(result.getPrintJobs().get(0).getId(), printJob.getId());
    }

}
