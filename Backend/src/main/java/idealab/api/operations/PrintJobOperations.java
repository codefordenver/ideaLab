package idealab.api.operations;

import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetAllPrintJobResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public GetAllPrintJobListResponse getAllPrintJobs(){
        // Some magic happened here !
        //TODO: Repo class should be coded !
        //TODO: They should be connected !
        //TODO: Domain entity (model) should be mapped response. It will be better if we use dto then map to response to make it more flexible for future use.
        //TODO: but now we don't need dto as mid entity between response entity and database entity.
        GetAllPrintJobResponse printJobResponse =  new GetAllPrintJobResponse();

        List<GetAllPrintJobResponse> printJobResponses = new ArrayList<GetAllPrintJobResponse>();
        printJobResponses.add(printJobResponse);

        return new GetAllPrintJobListResponse(printJobResponses);
    }

}
