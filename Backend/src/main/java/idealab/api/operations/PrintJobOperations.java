package idealab.api.operations;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobNewRequest;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.model.ColorType;
import idealab.api.model.EmailHash;
import idealab.api.model.PrintModel;
import idealab.api.repositories.ColorTypeRepo;
import idealab.api.repositories.EmailHashRepo;
import idealab.api.repositories.PrintModelRepo;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PrintJobOperations {
    private final DropboxOperations dropboxOperations;
    private PrintModelRepo printModelRepo;
    private ColorTypeRepo colorTypeRepo;
    private EmailHashRepo emailHashRepo;

    public PrintJobOperations(DropboxOperations dropboxOperations, PrintModelRepo printModelRepo,
            ColorTypeRepo colorTypeRepo, EmailHashRepo emailHashRepo) {
        this.dropboxOperations = dropboxOperations;
        this.printModelRepo = printModelRepo;
        this.colorTypeRepo = colorTypeRepo;
        this.emailHashRepo = emailHashRepo;
    }

    public GenericResponse newPrintJob(PrintJobNewRequest printJobNewRequest) {
        GenericResponse response = new GenericResponse();
        // Create new record based off of the printJobNewRequest
        EmailHash emailHash = new EmailHash(printJobNewRequest.getEmail());
        EmailHash newEmailHash = emailHashRepo.save(emailHash);

        String color = printJobNewRequest.getColor();
        String comments = printJobNewRequest.getComments();
        String dropboxLink = "temp";
        LocalDateTime currentTime = LocalDateTime.now();

        // Get Id of the request
        int file_Id = 103;
        // Save file to dropbox and return file path
        ColorType colorType = new ColorType(color);
        ColorType newColor = colorTypeRepo.save(colorType);

        // Create a model first with temp link
        PrintModel printModel = new PrintModel(newEmailHash, newColor, comments, dropboxLink, currentTime, currentTime);
        // Get id from new
        PrintModel newPrintModel = printModelRepo.save(printModel);
        // String filePath = dropboxOperations.uploadDropboxFile(file_Id,
        // printJobNewRequest.getFile());
        System.out.println(newPrintModel.toString());
        // Add file path to the record
        // Return success and new record to the frontend
        if (!newPrintModel.toString().isEmpty()) {
            response.setSuccess(true);
            response.setMessage(newPrintModel.toString());
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
