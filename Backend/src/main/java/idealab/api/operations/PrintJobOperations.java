package idealab.api.operations;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetAllPrintJobResponse;
import idealab.api.dto.response.GetPrintJobResponse;
import idealab.api.exception.ErrorType;
import idealab.api.model.*;
import idealab.api.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public GetPrintJobResponse newPrintJob(PrintJobNewRequest printJobNewRequest) {
        GetPrintJobResponse response = new GetPrintJobResponse();
        response.setSuccess(false);
        response.setMessage("File could not be uploaded");
        response.setHttpStatus(HttpStatus.BAD_REQUEST);

        if(printJobNewRequest.getFile() == null){
            response.setMessage("No file was submitted.  Please attach a file to the request");
            return response;
        }

        // Create new record based off of the printJobNewRequest
        String email = printJobNewRequest.getEmail();
        String customerFirstName = printJobNewRequest.getCustomerFirstName();
        String customerLastName = printJobNewRequest.getCustomerLastName();
        String color = printJobNewRequest.getColor();
        String comments = printJobNewRequest.getComments();
        LocalDateTime currentTime = LocalDateTime.now();

        // Check if EmailHash Exists otherwise make a new record
        // TODO: Hash email so it is not in plaintext!!
        String emailHash = printJobNewRequest.getEmail();
        EmailHash databaseEmail = emailHashRepo.findByEmailHash(emailHash);
        if (databaseEmail == null) {
            databaseEmail = new EmailHash(emailHash);
            emailHashRepo.save(databaseEmail);
        }

        // Create customer record with email hash if it does not already exist
        CustomerInfo customer = customerInfoRepo.findByEmailHashId(databaseEmail);
        if (customer == null) {
            customer = new CustomerInfo(databaseEmail, customerFirstName, customerLastName, email);
            customerInfoRepo.save(customer);
        }

        // Check if Color exists otherwise make a new record
        ColorType databaseColor = colorTypeRepo.findByColor(color);
        if (databaseColor == null) {
            databaseColor = new ColorType(color);
            colorTypeRepo.save(databaseColor);
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
        PrintJob printJob = new PrintJob(databaseEmail, databaseColor, databaseEmployee, Status.PENDING_REVIEW, comments, currentTime, currentTime);
        printJob = printJobRepo.save(printJob);

        System.out.println(printJob.toString());
        // TODO: set the queue position of the new job to be at the end of the list.

        // Make a dropbox sharable link here using the ID of the database record
        Map<String, String> data;
        data = dropboxOperations.uploadDropboxFile(printJob.getId(), printJobNewRequest.getFile());
        printJob.setDropboxPath(data.get("filePath"));
        printJob.setDropboxSharableLink(data.get("sharableLink"));

        printJob = printJobRepo.save(printJob);

        System.out.println(printJob.toString());
        List<PrintJob> printJobData = Arrays.asList(printJob);

        response.setSuccess(true);
        response.setMessage("Successfully saved new file to database!");
        response.setData(printJobData);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }

    public GetPrintJobResponse updateModel(Integer printId, PrintModelUpdateRequest model){
        GetPrintJobResponse response = new GetPrintJobResponse();

        MultipartFile file = model.getFile();

        if(file == null){
            response.setMessage("No file was submitted.  Please attach a file to the request");
            return response;
        }

        PrintJob printJob = printJobRepo.findPrintJobById(printId);

        Map<String, String> data = null;
        data = dropboxOperations.updateDropboxFile(printJob, model.getFile());
        printJob.setDropboxPath(data.get("filePath"));
        printJob.setDropboxSharableLink(data.get("sharableLink"));
        printJob.setDropboxPath("Error updating file");
        printJob.setDropboxSharableLink("Error updating file");

        printJobRepo.save(printJob);

        List<PrintJob> printJobData = Arrays.asList(printJob);

        response.setSuccess(true);
        response.setMessage("Successfully updated file to database!");
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

        dropboxOperations.deleteDropboxFile(printJob);
        printJob.setDropboxPath("Deleted");

        printJob.setDropboxSharableLink("Deleted");
        printJobRepo.save(printJob);

        response.setSuccess(true);
        response.setMessage("Successfully deleted file from DropBox");

        response.setHttpStatus(HttpStatus.ACCEPTED);
        return response;
    }

    public GenericResponse updatePrintJobStatus(Integer printId, PrintJobUpdateRequest dto) {
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

    public GetPrintJobResponse getAllPrintJobs() {
        GetPrintJobResponse response = new GetPrintJobResponse();
        response.setSuccess(false);
        response.setMessage("Could not return all print jobs");
        response.setHttpStatus(HttpStatus.BAD_REQUEST);

        List<PrintJob> printJobs = printJobRepo.findAll();

        if(printJobs == null || printJobs.size() == 0){
            ErrorType.PRINT_JOBS_NOT_EXIST.throwException();
        }

        response.setSuccess(true);
        response.setMessage("Successfully returned all print jobs");
        response.setData(printJobs);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }


    public GetAllPrintJobListResponse getDeletablePrintJobs() {
        List<Status> deletableStatuses = Arrays.asList(new Status[]{
            Status.PENDING_REVIEW,
            Status.FAILED,
            Status.PENDING_CUSTOMER_RESPONSE,
            Status.REJECTED,
            Status.COMPLETED,
            Status.ARCHIVED
        });
        List<PrintJob> printJobs = printJobRepo.findByStatusIn(deletableStatuses);
        List<GetAllPrintJobResponse> printJobResponses = printJobs.stream()
                .map(GetAllPrintJobResponse::new).collect(Collectors.toList());
        return new GetAllPrintJobListResponse(printJobResponses);
    }
}
