package idealab.api.operations;

import com.dropbox.core.DbxException;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.response.GetPrintJobDataResponse;
import idealab.api.model.*;
import idealab.api.repositories.ColorTypeRepo;
import idealab.api.repositories.CustomerInfoRepo;
import idealab.api.repositories.EmailHashRepo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetAllPrintJobResponse;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Component
public class PrintJobOperations {
    private final DropboxOperations dropboxOperations;
    private PrintJobRepo printJobRepo;
    private ColorTypeRepo colorTypeRepo;
    private EmailHashRepo emailHashRepo;
    private CustomerInfoRepo customerInfoRepo;
    private EmployeeRepo employeeRepo;

    public PrintJobOperations(DropboxOperations dropboxOperations, PrintJobRepo printJobRepo,
                              ColorTypeRepo colorTypeRepo, EmailHashRepo emailHashRepo, CustomerInfoRepo customerInfoRepo,
                              EmployeeRepo employeeRepo) {
        this.dropboxOperations = dropboxOperations;
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
        String employeeNotes = printJobNewRequest.getEmployeeNotes();
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
        PrintJob printJob = new PrintJob(databaseEmail, databaseColor, databaseEmployee, Status.PENDING_REVIEW, employeeNotes, comments, currentTime, currentTime);
        printJobRepo.save(printJob);

        System.out.println(printJob.toString());
        // TODO: set the queue position of the new job to be at the end of the list.

        // Make a dropbox sharable link here using the ID of the database record
        Map<String, String> data = null;
        try {
            data = dropboxOperations.uploadDropboxFile(printJob.getId(), printJobNewRequest.getFile());
            printJob.setDropboxPath(data.get("filePath"));
            printJob.setDropboxSharableLink(data.get("sharableLink"));
        } catch (IOException | DbxException e) {
            printJob.setDropboxPath("Error Creating New File");
            printJob.setDropboxSharableLink("Error Creating New File");
        }

        printJobRepo.save(printJob);

        System.out.println(printJob.toString());
        List<PrintJob> printJobData = Arrays.asList(printJob);

        response.setSuccess(true);
        response.setMessage("Successfully saved new file to database!");
        response.setData(printJobData);

        return response;
    }

    public GetPrintJobDataResponse updateModel(Integer printId, PrintModelUpdateRequest model){
        GetPrintJobDataResponse response = new GetPrintJobDataResponse();
        response.setSuccess(false);
        response.setMessage("File could not be updated");

        MultipartFile file = model.getFile();

        if(file == null){
            response.setMessage("No file was submitted.  Please attach a file to the request");
            return response;
        }

        PrintJob printJob = printJobRepo.findPrintJobById(printId);

        Map<String, String> data = null;
        try {
            data = dropboxOperations.updateDropboxFile(printJob, model.getFile());
            printJob.setDropboxPath(data.get("filePath"));
            printJob.setDropboxSharableLink(data.get("sharableLink"));
        } catch (IOException | DbxException e) {
            printJob.setDropboxPath("Error updating file");
            printJob.setDropboxSharableLink("Error updating file");
        }

        printJobRepo.save(printJob);

        List<PrintJob> printJobData = Arrays.asList(printJob);

        response.setSuccess(true);
        response.setMessage("Successfully updated file to database!");
        response.setData(printJobData);

        return response;
    }

    public GenericResponse deleteModel(Integer printId) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("File could not be deleted");

        PrintJob printJob = printJobRepo.findPrintJobById(printId);

        try {
            dropboxOperations.deleteDropboxFile(printJob);
            printJob.setDropboxPath("Deleted");
            printJob.setDropboxSharableLink("Deleted");
        } catch (DbxException e) {
            printJob.setDropboxPath("Error Deleting File");
            printJob.setDropboxSharableLink("Error Deleting File");
        }

        printJobRepo.save(printJob);

        response.setSuccess(true);
        response.setMessage("Successfully deleted file from DropBox");

        return response;
    }

    public GenericResponse updatePrintJob(PrintJobUpdateRequest dto) {
        GenericResponse response = new GenericResponse();

        // Prep for when we get data models merged to master
        // if(dto.isValidStatus())
        // {
        // //check if employee id is valid
        // EmployeeList employeeList =
        // employeeListRepo.getEmployeeListById(dto.getEmployeeId());
        //
        // //check if print id is valid
        // PrintStatus printStatus =
        // printStatusRepo.getPrintStatusById(dto.getPrintStatusId());
        //
        // if(employeeList != null && printStatus != null) {
        // //Update print status
        //
        //
        // //return success message
        // response.setSuccess(true);
        // response.setMessage("Print Job Updated");
        // }
        //
        // }
        //
        // return response;

        if (dto.isValidStatus()) {
            // check if employee id is valid
            // check if print id is valid
            // do any other logic to determine if the update is valid
            response.setSuccess(true);
            response.setMessage("Print Job Updated");
        } else {
            response.setSuccess(false);
            response.setMessage("Invalid Status");
        }
        return response;
    }


    public GenericResponse updatePrintJobStatus(Integer printId, PrintJobUpdateRequest dto) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Print Job Update Failed");

        if (dto.isValidStatus()) {
            Employee employee = employeeRepo.findEmployeeById(dto.getEmployeeId());
            PrintJob printJob = printJobRepo.findPrintJobById(printId);

            if (employee != null && printJob != null) {
                printJob = printJobRepo.save(printJob);
                if (printJob.getStatus().getName().equalsIgnoreCase(dto.getStatus())) {
                    response.setSuccess(true);
                    response.setMessage("Print Job Updated");
                }
            }
        } else {
            response.setMessage("Print Job Update Failed - Invalid Status");
        }

        return response;

    }

    public GenericResponse deletePrintJobStatus(PrintJobDeleteRequest dto) {

        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Print Job Delete Failed");

        Employee employee = employeeRepo.findEmployeeById(dto.getEmployeeId());
        PrintJob printJob = printJobRepo.findPrintJobById(dto.getPrintJobId());

        if (employee != null && printJob != null) {
            printJobRepo.delete(printJob);

            response.setSuccess(true);
            response.setMessage("Print Job Deleted Successfully");
        }

        return response;
    }

    public GetAllPrintJobListResponse getAllPrintJobs(){
        // Some magic happened here !
        //TODO: Repo class should be coded !
        //TODO: They should be connected !
        //TODO: Domain entity (model) should be mapped response. It will be better if we use dto then map to response to make it more flexible for future use.
        //TODO: but now we don't need dto as mid entity between response entity and database entity.

        //TODO: Tempporary
        GetAllPrintJobResponse printJobResponse =
                new GetAllPrintJobResponse(null, null, null, null, null,
                null, null, null);

        List<GetAllPrintJobResponse> printJobResponses = new ArrayList<GetAllPrintJobResponse>();
        printJobResponses.add(printJobResponse);

        return new GetAllPrintJobListResponse(printJobResponses);
    }

}
