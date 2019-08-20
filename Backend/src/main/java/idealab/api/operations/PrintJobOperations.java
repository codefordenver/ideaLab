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

    public GenericResponse newPrintJob(PrintJobNewRequest dto)
    {
        GenericResponse response = new GenericResponse();

        System.out.println(dto.getName()); 
        System.out.println(dto.getEmail());

        return response;
    }


    public GenericResponse newPrintJobFile(int id, MultipartFile file)
    {
        GenericResponse response = new GenericResponse();
        String fileProperties = dropboxOperations.uploadDropboxFile(id, file);

        if(!fileProperties.isEmpty())
        {
            response.setSuccess(true);
            response.setMessage(fileProperties);
        }
        else
        {
            response.setSuccess(false);
            response.setMessage("File could not be uploaded");
        }
        return response;

    }

    public GenericResponse updatePrintJob(PrintJobUpdateRequest dto)
    {
        GenericResponse response = new GenericResponse();

        if(dto.isValidStatus())
        {
            //check if employee id is valid
            //check if print id is valid
            //do any other logic to determine if the update is valid
            response.setSuccess(true);
            response.setMessage("Print Job Updated");
        }
        else
        {
            response.setSuccess(false);
            response.setMessage("Invalid Status");
        }
        return response;
    }


}
