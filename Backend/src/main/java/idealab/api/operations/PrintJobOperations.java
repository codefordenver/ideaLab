package idealab.api.operations;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobNewRequest;
import idealab.api.dto.PrintJobUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PrintJobOperations {
    private final DropboxOperations dropboxOperations;

    public PrintJobOperations(DropboxOperations dropboxOperations) {
        this.dropboxOperations = dropboxOperations;
    }

    public GenericResponse newPrintJob(PrintJobNewRequest printJobNewRequest) {
        GenericResponse response = new GenericResponse();
        // Create new record based off of the printJobNewRequest

        // Get Id of the request
        int file_Id = 103;

        //Save file to dropbox and return file path
        String filePath = dropboxOperations.uploadDropboxFile(file_Id, printJobNewRequest.getFile());

        //Add file path to the record

        //Return success and new record to the frontend
        if (!filePath.isEmpty()) {
            response.setSuccess(true);
            response.setMessage(filePath);
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
