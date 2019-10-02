package idealab.api.operations;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;
import static idealab.api.exception.ErrorType.DROPBOX_UPLOAD_FILE_ERROR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.ColorType;
import idealab.api.model.CustomerInfo;
import idealab.api.model.EmailHash;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.model.Queue;
import idealab.api.model.Status;
import idealab.api.repositories.ColorTypeRepo;
import idealab.api.repositories.CustomerInfoRepo;
import idealab.api.repositories.EmailHashRepo;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;

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
        PrintJobResponse result = operations.getAllPrintJobs();

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
        PrintJobResponse result = operations.getDeletablePrintJobs();

        // assert
        Assert.assertEquals(result.getData().get(0).getId(), printJob.getId());
    }


    @Test
    public void createNewPrintJob() {
        PrintJobResponse response = new PrintJobResponse();

        byte[] a = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
        MultipartFile file = new MockMultipartFile("Something", a);

        PrintJobNewRequest request = new PrintJobNewRequest();
        request.setColor("RED");
        request.setComments("COMMENTS");
        request.setCustomerFirstName("test");
        request.setCustomerLastName("testLast");
        request.setEmail("test@email.com");
        request.setFile(file);

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setFirstName("test");

        ColorType color = new ColorType();
        color.setColor("RED");
        color.setAvailable(true);

        Employee e = new Employee();
        e.setId(999);

        EmailHash emailHash = new EmailHash();
        emailHash.setEmailHash("test@email.com");

        Queue queue = new Queue(1);

        Map<String, String> data = new HashMap<>();
        data.put("filePath", "DROPBOX_PATH");
        data.put("sharableLink", "http://testlink.com");

        PrintJob printJob = new PrintJob();
        printJob.setColorTypeId(color);
        printJob.setComments("COMMENTS");
        printJob.setCreatedAt(LocalDateTime.now());
        printJob.setDropboxPath("DROPBOX_PATH");
        printJob.setDropboxSharableLink("http://testlink.com");
        printJob.setId(1);
        printJob.setUpdatedAt(LocalDateTime.now());
        printJob.setStatus(Status.PENDING_REVIEW);
        printJob.setEmployeeId(e);
        printJob.setEmailHashId(emailHash);
        printJob.setQueueId(queue);
        printJob.setUpdatedAt(LocalDateTime.now());

        List<PrintJob> printJobData = new ArrayList<>();
        printJobData.add(printJob);

        response.setSuccess(true);
        response.setMessage("Successfully saved new file to database!");
        response.setData(printJobData);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        when(emailHashRepo.findByEmailHash(any())).thenReturn(emailHash);
        when(customerInfoRepo.findByEmailHashId(any())).thenReturn(customerInfo);
        when(colorTypeRepo.findByColor(any())).thenReturn(color);
        when(employeeRepo.findEmployeeByUsername(any())).thenReturn(e);
        when(printJobRepo.save(any())).thenReturn(printJob);
        when(dropboxOperations.uploadDropboxFile(anyLong(), any())).thenReturn(data);

        PrintJobResponse opResponse = operations.newPrintJob(request);
        assert(opResponse.equals(response));
    }

    @Test
    public void createNewPrintJobSaveAllNonExistentData() {
        PrintJobResponse response = new PrintJobResponse();

        byte[] a = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
        MultipartFile file = new MockMultipartFile("Something", a);

        PrintJobNewRequest request = new PrintJobNewRequest();
        request.setColor("RED");
        request.setComments("COMMENTS");
        request.setCustomerFirstName("test");
        request.setCustomerLastName("testLast");
        request.setEmail("test@email.com");
        request.setFile(file);

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setFirstName("test");

        ColorType color = new ColorType();
        color.setColor("RED");
        color.setAvailable(true);

        Employee e = new Employee();
        e.setId(999);

        EmailHash emailHash = new EmailHash();
        emailHash.setEmailHash("test@email.com");

        Queue queue = new Queue(1);

        Map<String, String> data = new HashMap<>();
        data.put("filePath", "DROPBOX_PATH");
        data.put("sharableLink", "http://testlink.com");

        PrintJob printJob = new PrintJob();
        printJob.setColorTypeId(color);
        printJob.setComments("COMMENTS");
        printJob.setCreatedAt(LocalDateTime.now());
        printJob.setDropboxPath("DROPBOX_PATH");
        printJob.setDropboxSharableLink("http://testlink.com");
        printJob.setId(1);
        printJob.setUpdatedAt(LocalDateTime.now());
        printJob.setStatus(Status.PENDING_REVIEW);
        printJob.setEmployeeId(e);
        printJob.setEmailHashId(emailHash);
        printJob.setQueueId(queue);
        printJob.setUpdatedAt(LocalDateTime.now());

        List<PrintJob> printJobData = new ArrayList<>();
        printJobData.add(printJob);


        response.setSuccess(true);
        response.setMessage("Successfully saved new file to database!");
        response.setData(printJobData);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        when(emailHashRepo.findByEmailHash(any())).thenReturn(null);
        when(emailHashRepo.save(any())).thenReturn(emailHash);
        when(customerInfoRepo.findByEmailHashId(any())).thenReturn(null);
        when(customerInfoRepo.save(any())).thenReturn(customerInfo);
        when(colorTypeRepo.findByColor(any())).thenReturn(color);
        when(employeeRepo.findEmployeeByUsername(any())).thenReturn(null);
        when(employeeRepo.save(any())).thenReturn(e);
        when(printJobRepo.save(any())).thenReturn(printJob);
        when(dropboxOperations.uploadDropboxFile(anyLong(), any())).thenReturn(data);

        PrintJobResponse opResponse = operations.newPrintJob(request);

        assert(opResponse.equals(response));
    }

    @Test(expected = IdeaLabApiException.class)
    public void createNewPrintJobWithNotFoundColor() {
    	// given
    	byte[] a = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
        MultipartFile file = new MockMultipartFile("Something", a);

        PrintJobNewRequest request = new PrintJobNewRequest();
        request.setColor("RED");
        request.setComments("COMMENTS");
        request.setCustomerFirstName("test");
        request.setCustomerLastName("testLast");
        request.setEmail("test@email.com");
        request.setFile(file);

        when(colorTypeRepo.findByColor(any())).thenReturn(null);

        // when
        operations.newPrintJob(request);
        
        // assert
        verify(operations, times(1)).newPrintJob(request);
    }
    
    @Test(expected = IdeaLabApiException.class)
    public void createNewPrintJobNullFile() {
        PrintJobResponse response = new PrintJobResponse();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("No file was submitted.  Please attach a file to the request");
        response.setSuccess(false);

        PrintJobNewRequest request = new PrintJobNewRequest();
        request.setColor("RED");
        request.setComments("COMMENTS");
        request.setCustomerFirstName("test");
        request.setCustomerLastName("testLast");
        request.setEmail("test@email.com");
        request.setFile(null);

        operations.newPrintJob(request);

    }

    @Test
    public void updateModelSuccess() {
        byte[] a = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
        MultipartFile file = new MockMultipartFile("Something", a);

        PrintModelUpdateRequest request = new PrintModelUpdateRequest();
        request.setFile(file);

        PrintJob printJob = new PrintJob();
        printJob.setId(999);

        PrintJobResponse response = new PrintJobResponse();
        response.setSuccess(true);
        response.setMessage("Successfully updated file to database!");
        response.setHttpStatus(HttpStatus.ACCEPTED);
        List<PrintJob> printJobs = new ArrayList<>();
        printJobs.add(printJob);
        response.setData(printJobs);

        Map<String, String> data = new HashMap<>();
        data.put("filePath", "DROPBOX_PATH");
        data.put("sharableLink", "http://testlink.com");

        when(printJobRepo.findPrintJobById(printJob.getId())).thenReturn(printJob);
        when(dropboxOperations.updateDropboxFile(printJob, request.getFile())).thenReturn(data);
        when(printJobRepo.save(printJob)).thenReturn(printJob);

        PrintJobResponse opResponse = operations.updateModel(printJob.getId(), request);

        assert(opResponse.equals(response));
    }

    @Test(expected = IdeaLabApiException.class)
    public void updateModelPrintJobNotFound() {
        byte[] a = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
        MultipartFile file = new MockMultipartFile("Something", a);

        PrintModelUpdateRequest request = new PrintModelUpdateRequest();
        request.setFile(file);

        PrintJob printJob = new PrintJob();
        printJob.setId(999);

        when(printJobRepo.findPrintJobById(printJob.getId())).thenReturn(null);

        operations.updateModel(printJob.getId(), request);
    }

    @Test(expected = IdeaLabApiException.class)
    public void updateModelDropboxFail() {
        byte[] a = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
        MultipartFile file = new MockMultipartFile("Something", a);

        PrintModelUpdateRequest request = new PrintModelUpdateRequest();
        request.setFile(file);

        PrintJob printJob = new PrintJob();
        printJob.setId(999);

        when(printJobRepo.findPrintJobById(printJob.getId())).thenReturn(printJob);
        when(dropboxOperations.updateDropboxFile(printJob, request.getFile())).thenThrow(new IdeaLabApiException(DROPBOX_UPLOAD_FILE_ERROR));

        operations.updateModel(printJob.getId(), request);
    }

    @Test
    public void deleteModelSuccess() {
        GenericResponse response = new GenericResponse();
        response.setSuccess(true);
        response.setMessage("Successfully deleted file from DropBox");
        response.setHttpStatus(HttpStatus.ACCEPTED);

        PrintJob printJob = new PrintJob();
        printJob.setId(999);

        when(printJobRepo.findPrintJobById(printJob.getId())).thenReturn(printJob);
        doNothing().when(dropboxOperations).deleteDropboxFile(printJob);
        when(printJobRepo.save(printJob)).thenReturn(printJob);

        GenericResponse opResponse = operations.deleteModel(printJob.getId());

        assert(opResponse.equals(response));
    }

    @Test(expected = IdeaLabApiException.class)
    public void deleteModelPrintJobNotFound() {
        PrintJob printJob = new PrintJob();
        printJob.setId(999);

        when(printJobRepo.findPrintJobById(printJob.getId())).thenReturn(null);

        operations.deleteModel(printJob.getId());
    }
}
