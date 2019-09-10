package idealab.api.operations;

import com.dropbox.core.DbxException;
import idealab.api.dto.request.*;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetAllPrintJobResponse;
import idealab.api.dto.response.GetPrintJobDataResponse;
import idealab.api.exception.ErrorType;
import idealab.api.model.*;
import idealab.api.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PrintJobOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintJobOperations.class);

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

    public GetPrintJobDataResponse newPrintJob(PrintJobNewRequest printJobNewRequest) {
        GetPrintJobDataResponse response = new GetPrintJobDataResponse();
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
        printJobRepo.save(printJob);

        System.out.println(printJob.toString());
        // TODO: set the queue position of the new job to be at the end of the list.

        // Make a dropbox sharable link here using the ID of the database record
        Map<String, String> data;
        data = dropboxOperations.uploadDropboxFile(printJob.getId(), printJobNewRequest.getFile());
        printJob.setDropboxPath(data.get("filePath"));
        printJob.setDropboxSharableLink(data.get("sharableLink"));

        printJobRepo.save(printJob);

        System.out.println(printJob.toString());
        List<PrintJob> printJobData = Arrays.asList(printJob);

        response.setSuccess(true);
        response.setMessage("Successfully saved new file to database!");
        response.setData(printJobData);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }

    public GetPrintJobDataResponse updateModel(Integer printId, PrintModelUpdateRequest model){
        GetPrintJobDataResponse response = new GetPrintJobDataResponse();

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

    public GetAllPrintJobListResponse getAllPrintJobs() {
        List<PrintJob> printJobs = printJobRepo.findAll();

        if(printJobs == null || printJobs.size() == 0){
            ErrorType.PRINT_JOBS_NOT_EXIST.throwException();
        }

        List<GetAllPrintJobResponse> printJobResponses = printJobs.stream()
                .map(GetAllPrintJobResponse::new).collect(Collectors.toList());

        return new GetAllPrintJobListResponse(printJobResponses);
    }

    public GenericResponse processPrintJobUpload(MultipartFile file) {
        GenericResponse response = new GenericResponse();
        List<CsvUploadRequest> requests = new ArrayList<>();
        try {
            byte[] bytes = file.getBytes();
            ByteArrayInputStream inputFilestream = new ByteArrayInputStream(bytes);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFilestream));
            String line = "";
            List<String> items;
            while ((line = br.readLine()) != null) {
                items = Arrays.asList(line.split("\\s*,\\s*"));
                CsvUploadRequest request = new CsvUploadRequest();
                request.setFirstName(items.get(0));
                request.setLastName(items.get(1));
                request.setEmail(items.get(2));
                request.setColor(items.get(3));
                request.setEmployeeId(Integer.valueOf(items.get(4)));
                request.setStatus(items.get(5));

                try {
                    Employee e = employeeRepo.findEmployeeById(request.getEmployeeId());
                    if(e == null) {
                        LOGGER.error("CSV line not processed: Employee not valid\n" + line);
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("CSV line not processed: Error getting Employee\n" + line);
                    continue;
                }

                Status status = Status.valueOf(request.getStatus());
                if(status == null) {
                    LOGGER.error("CSV line not processed: Status not valid\n" + line);
                    continue;
                }

                requests.add(request);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Upload Failed");
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            return response;
        }
        response.setSuccess(true);
        response.setMessage("Upload Successful");
        response.setHttpStatus(HttpStatus.ACCEPTED);
        return response;
    }

}
