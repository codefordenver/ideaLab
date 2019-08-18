package idealab.api.operations;

import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GetAllPrintJobResponse;
import org.springframework.stereotype.Component;

@Component
public class PrintJobOperations {

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

    public GetAllPrintJobResponse getAllPrintJobs(){
        // Some magic happened here !
        //TODO: Repo class should be coded !
        //TODO: They should be connected !
        return new GetAllPrintJobResponse();
    }

}
