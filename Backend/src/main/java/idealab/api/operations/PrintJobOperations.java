package idealab.api.operations;

import static idealab.api.exception.ErrorType.COLOR_CANT_FIND_BY_TYPE;
import static idealab.api.exception.ErrorType.DROPBOX_UPLOAD_FILE_ERROR;
import static idealab.api.exception.ErrorType.PRINT_JOBS_NOT_EXIST;
import static idealab.api.exception.ErrorType.PRINT_JOB_CANT_FIND_BY_ID;
import static idealab.api.exception.ErrorType.EMAIL_CANT_FIND_BY_HASH;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.exception.ErrorType;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.ColorType;
import idealab.api.model.CustomerInfo;
import idealab.api.model.EmailHash;
import idealab.api.model.Employee;
import idealab.api.model.EmployeeRole;
import idealab.api.model.PrintJob;
import idealab.api.model.Status;
import idealab.api.repositories.ColorTypeRepo;
import idealab.api.repositories.CustomerInfoRepo;
import idealab.api.repositories.EmailHashRepo;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;

@Service
public class PrintJobOperations {
    private final DropboxOperations dropboxOperations;
    private final PrintJobRepo printJobRepo;
    private final ColorTypeRepo colorTypeRepo;
    private final EmailHashRepo emailHashRepo;
    private final CustomerInfoRepo customerInfoRepo;
    private final EmployeeRepo employeeRepo;

    public PrintJobOperations(DropboxOperations dropboxOperations, PrintJobRepo printJobRepo,
                              ColorTypeRepo colorTypeRepo, EmailHashRepo emailHashRepo, CustomerInfoRepo customerInfoRepo,
                              EmployeeRepo employeeRepo) {

        this.dropboxOperations = dropboxOperations; // service'lerin üstüne bir layer daha cekmek lazim aslında belki de ? cok fazla dependent burada cunku
        this.printJobRepo = printJobRepo;
        this.colorTypeRepo = colorTypeRepo;
        this.emailHashRepo = emailHashRepo;
        this.customerInfoRepo = customerInfoRepo;
        this.employeeRepo = employeeRepo;
    }

    public PrintJobResponse newPrintJob(PrintJobNewRequest printJobNewRequest) {
        printJobNewRequest.validate();
        PrintJobResponse response = new PrintJobResponse("File could not be uploaded");

        if (printJobNewRequest.getFile() == null) {
            throw new IdeaLabApiException(DROPBOX_UPLOAD_FILE_ERROR);
        }

        String email = printJobNewRequest.getEmail();
        String customerFirstName = printJobNewRequest.getCustomerFirstName();
        String customerLastName = printJobNewRequest.getCustomerLastName();
        String color = printJobNewRequest.getColor();
        String comments = printJobNewRequest.getComments();
        LocalDateTime currentTime = LocalDateTime.now();

        // TODO: Hash email so it is not in plaintext!!
        String emailHash = printJobNewRequest.getEmail();
        EmailHash databaseEmail = emailHashRepo.findByEmailHash(emailHash);
        
        if (databaseEmail == null) {
        	throw new IdeaLabApiException(EMAIL_CANT_FIND_BY_HASH);
        }

        CustomerInfo customer = customerInfoRepo.findByEmailHashId(databaseEmail);
        
        if (customer == null) {
            customer = new CustomerInfo(databaseEmail, customerFirstName, customerLastName, email);
            customer = customerInfoRepo.save(customer);
        }

        ColorType databaseColor = colorTypeRepo.findByColor(color);
        
        if (databaseColor == null) {
        	throw new IdeaLabApiException(COLOR_CANT_FIND_BY_TYPE);
        }

        // TODO: Remove temp employee, this should be taken directly from the employee making the request through the token.
        String tempEmployeeFirstName = "Temp John";
        String tempEmployeeLastName = "Temp Joe";
        String tempEmployeeUserName = "Temp Cotton Eyed Joe";

        Employee employee = new Employee(tempEmployeeUserName, "such secure, wow!", EmployeeRole.STAFF, tempEmployeeFirstName, tempEmployeeLastName);
        Employee databaseEmployee = employeeRepo.findEmployeeByUsername(employee.getUsername());

        if (databaseEmployee == null) {
            databaseEmployee = employeeRepo.save(employee);
        }

        // Create a new print model first with temp dropbox link
        PrintJob printJob = new PrintJob(databaseEmail, databaseColor, databaseEmployee, Status.PENDING_REVIEW, comments);

        // TODO: set the queue position of the new job to be at the end of the list.

        // Make a dropbox sharable link here using the ID of the database record
        Map<String, String> data = dropboxOperations.uploadDropboxFile(currentTime.toLocalTime().toNanoOfDay(), printJobNewRequest.getFile());

        printJob.setDropboxPath(data.get("filePath"));
        printJob.setDropboxSharableLink(data.get("sharableLink"));
        printJob = printJobRepo.save(printJob);

        return getPrintJobResponse(response, printJob, data, "Successfully saved new file to database!");
    }

    public PrintJobResponse updateModel(Integer printId, PrintModelUpdateRequest model){
        model.validate();
        PrintJobResponse response = new PrintJobResponse("Model could not be updated");

        PrintJob printJob = printJobRepo.findPrintJobById(printId);
        if(printJob == null) {
            throw new IdeaLabApiException(PRINT_JOB_CANT_FIND_BY_ID);
        }

        Map<String, String> data;
        data = dropboxOperations.updateDropboxFile(printJob, model.getFile());

        printJob.setDropboxPath(data.get("filePath"));
        printJob.setDropboxSharableLink(data.get("sharableLink"));
        printJob = printJobRepo.save(printJob);

        return getPrintJobResponse(response, printJob, data, "Successfully updated file to database!");
    }

    private PrintJobResponse getPrintJobResponse(PrintJobResponse response, PrintJob printJob, Map<String, String> data, String s) {
        List<PrintJob> printJobData = Arrays.asList(printJob);

        response.setSuccess(true);
        response.setMessage(s);
        response.setData(printJobData);

        response.setHttpStatus(HttpStatus.ACCEPTED);
        return response;
    }

    public GenericResponse deleteModel(Integer printId) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("File could not be deleted");
        response.setHttpStatus(HttpStatus.BAD_REQUEST);

        PrintJob printJob = printJobRepo.findPrintJobById(printId);

        if(printJob == null) {
            throw new IdeaLabApiException(PRINT_JOBS_NOT_EXIST);
        }

        dropboxOperations.deleteDropboxFile(printJob);
        printJob.setDropboxPath("Deleted");

        printJob.setDropboxSharableLink("Deleted");
        printJob = printJobRepo.save(printJob);

        response.setSuccess(true);
        response.setMessage("Successfully deleted file from DropBox");

        response.setHttpStatus(HttpStatus.ACCEPTED);
        return response;
    }

    public GenericResponse updatePrintJobStatus(Integer printId, PrintJobUpdateRequest dto) {
        dto.validate();
        Status requestStatus = Status.fromValue(dto.getStatus());

        if(requestStatus == null || !requestStatus.isValid()){
            ErrorType.REQUEST_STATUS_IS_NOT_VALID.throwException();
        }

        Employee employee = employeeRepo.findEmployeeById(dto.getEmployeeId());
        PrintJob printJob = printJobRepo.findPrintJobById(printId);

        if(employee == null || printJob == null){
            ErrorType.PRINT_JOB_UPDATE_FAILED.throwException();
        }

        GenericResponse response = new GenericResponse();

        printJob.setStatus(requestStatus);
        printJobRepo.save(printJob);

        response.setSuccess(true);
        response.setMessage("Print Job Updated");
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }

    public GenericResponse deletePrintJob(PrintJobDeleteRequest dto) {
        dto.validate();
        Employee employee = employeeRepo.findEmployeeById(dto.getEmployeeId());
        PrintJob printJob = printJobRepo.findPrintJobById(dto.getPrintJobId());

        if(employee == null || printJob == null){
            ErrorType.PRINT_JOB_CANT_DELETED.throwException();
        }

        GenericResponse response = new GenericResponse();

        printJobRepo.delete(printJob);

        response.setSuccess(true);
        response.setMessage("Print Job Deleted Successfully");
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }

    public PrintJobResponse getAllPrintJobs(String status) {
        PrintJobResponse response = new PrintJobResponse("Could not get all print jobs");

        List<PrintJob> printJobs = status == null? printJobRepo.findAll() : printJobRepo.findPrintJobByStatus(Status.fromValue(status));

        if (printJobs == null || printJobs.isEmpty()){
            ErrorType.PRINT_JOBS_NOT_EXIST.throwException();
        }

        response.setSuccess(true);
        response.setMessage(status == null? "Successfully returned all print jobs" : "Successfully returned print jobs by " + status + " status");
        response.setData(printJobs);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }

    public PrintJobResponse getDeletablePrintJobs() {
        PrintJobResponse response = new PrintJobResponse("Could not get deletable print jobs");
        List<Status> deletableStatuses = Arrays.asList(new Status[]{
            Status.PENDING_REVIEW,
            Status.FAILED,
            Status.PENDING_CUSTOMER_RESPONSE,
            Status.REJECTED,
            Status.COMPLETED,
            Status.ARCHIVED
        });
        List<PrintJob> printJobs = printJobRepo.findByStatusIn(deletableStatuses);

        response.setSuccess(true);
        response.setMessage("Successfully returned deletable print jobs");
        response.setData(printJobs);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }
}
