package idealab.api.operations;

import static idealab.api.exception.ErrorType.COLOR_CANT_FIND_BY_TYPE;
import static idealab.api.exception.ErrorType.DROPBOX_UPLOAD_FILE_ERROR;
import static idealab.api.exception.ErrorType.GENERAL_ERROR;
import static idealab.api.exception.ErrorType.PRINT_JOBS_NOT_EXIST;
import static idealab.api.exception.ErrorType.PRINT_JOB_CANT_FIND_BY_ID;
import static idealab.api.exception.ErrorType.PRINT_JOB_UPDATE_FAILED;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.request.UpdatePrintJobPropertiesRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.exception.ErrorType;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.ColorType;
import idealab.api.model.CustomerInfo;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.model.Queue;
import idealab.api.model.Status;
import idealab.api.repositories.ColorTypeRepo;
import idealab.api.repositories.CustomerInfoRepo;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;
import idealab.api.repositories.QueueRepo;
import idealab.api.service.EmailHashUtil;
import idealab.api.service.FileService;

@Service
public class PrintJobOperations {
    private final FileService fileService;
    private final PrintJobRepo printJobRepo;
    private final ColorTypeRepo colorTypeRepo;
    private final CustomerInfoRepo customerInfoRepo;
    private final QueueRepo queueRepo;
    private final EmployeeRepo employeeRepo;
    private final EmailHashUtil emailHashUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintJobOperations.class);

    public PrintJobOperations(FileService fileService, PrintJobRepo printJobRepo,
                              ColorTypeRepo colorTypeRepo, CustomerInfoRepo customerInfoRepo,
                              EmployeeRepo employeeRepo, EmailHashUtil emailHashUtil, QueueRepo queueRepo) {

        this.fileService = fileService;
        this.printJobRepo = printJobRepo;
        this.colorTypeRepo = colorTypeRepo;
        this.customerInfoRepo = customerInfoRepo;
        this.employeeRepo = employeeRepo;
        this.queueRepo = queueRepo;
        this.emailHashUtil = emailHashUtil;
    }

    public DataResponse<PrintJob> newPrintJob(PrintJobNewRequest printJobNewRequest, Principal principal) {
        printJobNewRequest.validate();
        DataResponse<PrintJob> response = new DataResponse<PrintJob>("File could not be uploaded");

        if (printJobNewRequest.getFile() == null) {
            throw new IdeaLabApiException(DROPBOX_UPLOAD_FILE_ERROR);
        }

        String email = printJobNewRequest.getEmail();
        String emailHash = emailHashUtil.MD5Hash(email);
        String customerFirstName = printJobNewRequest.getCustomerFirstName();
        String customerLastName = printJobNewRequest.getCustomerLastName();
        String color = printJobNewRequest.getColor();
        String comments = printJobNewRequest.getComments();

        LocalDateTime currentTime = LocalDateTime.now();

        CustomerInfo customer = customerInfoRepo.findByEmail(email);
        if (customer == null) {
            customer = new CustomerInfo(null, customerFirstName, customerLastName, email);
            customer = customerInfoRepo.save(customer);
        }

        if(customer.getPrintJobs() != null && customer.getPrintJobs().size() > 5) {
            int countForDay = 0;
            for(PrintJob p : customer.getPrintJobs()) {
                if(p.getCreatedAt().plusHours(24).isAfter(LocalDateTime.now())) {
                    countForDay++;
                }
            }
            if(countForDay > 5) {
                throw new IdeaLabApiException(GENERAL_ERROR, "Customer has already created 5 print jobs in the last 24 hours");
            }
        }

        ColorType databaseColor = colorTypeRepo.findByColor(color);
        if (databaseColor == null) {
        	throw new IdeaLabApiException(COLOR_CANT_FIND_BY_TYPE);
        }

        // Because an Employee will always be authenticated to use this endpoint,
        // we shouldn't need error checking here because principal will never be null
        Employee databaseEmployee = employeeRepo.findEmployeeByUsername(principal.getName());

        PrintJob printJob = new PrintJob(customer, databaseColor, databaseEmployee, Status.PENDING_REVIEW, comments, emailHash);

        // Make a dropbox sharable link here using the time of the database record
        Map<String, String> data = fileService.uploadFile(currentTime.toLocalTime().toNanoOfDay(), printJobNewRequest.getFile());
        printJob.setFilePath(data.get("filePath"));
        printJob.setFileSharableLink(data.get("sharableLink"));

        Set<PrintJob> printJobs;

        if(customer.getPrintJobs() == null) {
            printJobs = new HashSet<>();
        } else {
            printJobs = customer.getPrintJobs();
        }
        printJobs.add(printJob);
        customer.setPrintJobs(printJobs);
        printJob = printJobRepo.save(printJob);

        // If there is only one print job for the given customer (itself), add the rank to the bottom of the queue.
        // We only need to add it to the rank table if there is not currently a print job from this customer in the
        // rank table. Otherwise, they will be added when the most recent print job has changed.
        if (printJobRepo.findByCustomerInfo(customer).size() == 1) {
            Queue queue = new Queue(printJob, queueRepo.getMaximumRank()+1);
            queue = queueRepo.save(queue);
        }

        return getPrintJobResponse(response, printJob, data, "Successfully saved new file to database!");
    }

    public DataResponse<PrintJob> updateModel(Integer printId, PrintModelUpdateRequest model){
        model.validate();
        DataResponse<PrintJob> response = new DataResponse<PrintJob>("Model could not be updated");

        PrintJob printJob = printJobRepo.findPrintJobById(printId);
        if(printJob == null) {
            throw new IdeaLabApiException(PRINT_JOB_CANT_FIND_BY_ID);
        }

        Map<String, String> data;
        data = fileService.updateFile(printJob, model.getFile());

        printJob.setFilePath(data.get("filePath"));
        printJob.setFileSharableLink(data.get("sharableLink"));
        printJob = printJobRepo.save(printJob);

        return getPrintJobResponse(response, printJob, data, "Successfully updated file to database!");
    }

    private DataResponse<PrintJob> getPrintJobResponse(DataResponse<PrintJob> response, PrintJob printJob, Map<String, String> data, String s) {
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

        fileService.deleteFile(printJob.getFilePath());
        printJob.setFilePath("Deleted");

        printJob.setFileSharableLink("Deleted");
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

        // If the status is changed to final, delete the print job rank from the rank table
        // and check if another one needs to be added
        if (requestStatus == Status.REJECTED || requestStatus == Status.COMPLETED) {
            // Delete rank from queue table
            Queue deleteQueue = queueRepo.findByPrintJobId(printJob);
            queueRepo.delete(deleteQueue);

            // Get list of print jobs for customer + remove any completed/rejected
            // + get oldest date in list
            CustomerInfo customer = printJob.getCustomerInfo();
            List<PrintJob> printJobs = printJobRepo.findByCustomerInfo(customer);
            printJobs.removeIf(job -> (job.getStatus() == Status.REJECTED || job.getStatus() == Status.COMPLETED));

            if (printJobs.size() > 1) {
                Collections.sort(printJobs);
            }

            // Add most recent one to Queue table
            if (printJobs.size() > 0) {
                Queue newQueue = new Queue(printJobs.get(0), queueRepo.getMaximumRank()+1);
                newQueue = queueRepo.save(newQueue);
            }
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

    public DataResponse<PrintJob> getAllPrintJobs(String status) {
        DataResponse<PrintJob> response = new DataResponse<PrintJob>("Could not get all print jobs");

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

    public DataResponse<PrintJob> getDeletablePrintJobs() {
        DataResponse<PrintJob> response = new DataResponse<PrintJob>("Could not get deletable print jobs");
        List<Status> deletableStatuses = Arrays.asList(Status.PENDING_REVIEW,
            Status.FAILED,
            Status.PENDING_CUSTOMER_RESPONSE,
            Status.REJECTED,
            Status.COMPLETED,
            Status.ARCHIVED);
        List<PrintJob> printJobs = printJobRepo.findByStatusIn(deletableStatuses);

        response.setSuccess(true);
        response.setMessage("Successfully returned deletable print jobs");
        response.setData(printJobs);
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return response;
    }

    public DataResponse<PrintJob> updatePrintJobProps(Integer printJobId, UpdatePrintJobPropertiesRequest request) {
        request.validate();
        boolean isChanged = false;
        LOGGER.info("PJO:" + printJobId + " " + request);
        PrintJob printJob = printJobRepo.findPrintJobById(printJobId);
        if(printJob == null)
            throw new IdeaLabApiException(PRINT_JOB_CANT_FIND_BY_ID);

        ColorType colorType = null;
        if(request.getColorType() != null && !request.getColorType().trim().isEmpty()) {
            colorType = colorTypeRepo.findByColor(request.getColorType());
            if (colorType == null)
                throw new IdeaLabApiException(PRINT_JOB_UPDATE_FAILED, "Color type is invalid");
        }

        if(request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            isChanged = true;
            printJob.setStatus(Status.fromValue(request.getStatus()));
        }

        if(request.getComments() != null) {
            isChanged = true;
            printJob.setComments(request.getComments());
        }

        if(colorType != null && colorType.getColor() != null && !colorType.getColor().trim().isEmpty()) {
            isChanged = true;
            printJob.setColorType(colorType);
        }

        // Dont save if nothing actually updated
        if(isChanged)
            printJobRepo.save(printJob);

        List<PrintJob> printJobs = new ArrayList<>();
        printJobs.add(printJob);

        DataResponse<PrintJob> response = new DataResponse<PrintJob>();
        response.setSuccess(true);
        response.setMessage("Print job properties updated successfully");
        response.setHttpStatus(HttpStatus.ACCEPTED);
        response.setData(printJobs);

        return response;
    }

    public DataResponse<PrintJob> getPrintJobById(Integer printJobId){
        PrintJob printJob = printJobRepo.findPrintJobById(printJobId);
        if(printJob == null)
            throw new IdeaLabApiException(PRINT_JOB_CANT_FIND_BY_ID);

        List<PrintJob> printJobs = new ArrayList<>();
        printJobs.add(printJob);

        DataResponse<PrintJob> response = new DataResponse<PrintJob>();
        response.setSuccess(true);
        response.setMessage("Print job returned successfully");
        response.setHttpStatus(HttpStatus.ACCEPTED);
        response.setData(printJobs);

        return response;
    }
}
