package idealab.api.operations;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobData;
import idealab.api.dto.PrintJobNewRequest;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.model.ColorType;
import idealab.api.model.CustomerInfo;
import idealab.api.model.EmailHash;
import idealab.api.model.PrintModel;
import idealab.api.repositories.ColorTypeRepo;
import idealab.api.repositories.CustomerInfoRepo;
import idealab.api.repositories.EmailHashRepo;
import idealab.api.repositories.PrintModelRepo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PrintJobOperations {
    private final DropboxOperations dropboxOperations;
    private PrintModelRepo printModelRepo;
    private ColorTypeRepo colorTypeRepo;
    private EmailHashRepo emailHashRepo;
    private CustomerInfoRepo customerInfoRepo;

    public PrintJobOperations(DropboxOperations dropboxOperations, PrintModelRepo printModelRepo,
            ColorTypeRepo colorTypeRepo, EmailHashRepo emailHashRepo, CustomerInfoRepo customerInfoRepo) {
        this.dropboxOperations = dropboxOperations;
        this.printModelRepo = printModelRepo;
        this.colorTypeRepo = colorTypeRepo;
        this.emailHashRepo = emailHashRepo;
        this.customerInfoRepo = customerInfoRepo;
    }

    public PrintJobData newPrintJob(PrintJobNewRequest printJobNewRequest) {
        PrintJobData response = new PrintJobData();
        // Create new record based off of the printJobNewRequest
        String firstName = printJobNewRequest.getFirstName();
        String lastName = printJobNewRequest.getLastName();
        String email = printJobNewRequest.getEmail();
        String color = printJobNewRequest.getColor();
        String comments = printJobNewRequest.getComments();
        String dropboxLink = "temp"; // ! This should always be temporary and the actual file is uploaded with the id of the record!
        LocalDateTime currentTime = LocalDateTime.now();

        // Check if EmailHash Exists otherwise make a new record
        // TODO: Hash email so it is not in plaintext
        String emailHash = printJobNewRequest.getEmail();
        EmailHash databaseEmail = emailHashRepo.findByEmailHash(emailHash);
        if (databaseEmail == null) {
            databaseEmail = new EmailHash(emailHash);
            emailHashRepo.save(databaseEmail);
        }

        // Create customer record with email hash if it does not already exist
        CustomerInfo customer = customerInfoRepo.findByEmailHashId(databaseEmail);
        if (customer == null) {
            customer = new CustomerInfo(databaseEmail, firstName, lastName, email);
            customerInfoRepo.save(customer);
        }

        // Check if Color exists otherwise make a new record
        ColorType databaseColor = colorTypeRepo.findByColor(color);
        if (databaseColor == null) {
            databaseColor = new ColorType(color);
            colorTypeRepo.save(databaseColor);
        }

        // Create a new print model first with temp dropbox link
        PrintModel printModel = new PrintModel(databaseEmail, databaseColor, comments, dropboxLink, currentTime,
                currentTime);
        printModelRepo.save(printModel);

        // Make a dropbox sharable link here using the ID of the database record
        String filePath = dropboxOperations.uploadDropboxFile(printModel.getId(), printJobNewRequest.getFile());
        printModel.setDropboxLink(filePath);
        printModelRepo.save(printModel);
        
        List<PrintModel> printModelData = Arrays.asList(printModel);

        // TODO: Determine alternate method of how to handle dead code in else statement.  Try catch or throw exception?
        if (printModel != null) {
            response.setSuccess(true);
            response.setMessage("Successfully saved new file to database!");
            response.setData(printModelData);
        } else {
            response.setSuccess(false);
            response.setMessage("File could not be uploaded");
        }
        return response;
    }

    public GenericResponse updateFile(int fileId, MultipartFile file) {
        GenericResponse response = new GenericResponse();

        // //Get existing filePath from the Print Job model and delete the old file
        // String fileDeleted = dropboxOperations.deletedropboxFile(filePath);

        // Create a new file with the fileId and new filename
        String fileProperties = dropboxOperations.uploadDropboxFile(fileId, file);

        // Associate file with

        if (!fileProperties.isEmpty()) {
            response.setSuccess(true);
            response.setMessage(fileProperties);
        } else {
            response.setSuccess(false);
            response.setMessage("File could not be uploaded");
        }
        return response;

    }

    // EmployeeListRepo employeeListRepo;
    // PrintStatusRepo printStatusRepo;

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

}
