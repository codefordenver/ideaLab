package idealab.api.operations;

import idealab.api.dto.requests.PrintJobDeleteRequest;
import idealab.api.dto.requests.PrintJobUpdateRequest;
import idealab.api.dto.responses.GenericResponse;
import idealab.api.dto.responses.GetAllPrintJobListResponse;
import idealab.api.dto.responses.GetAllPrintJobResponse;
import idealab.api.exception.ErrorType;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrintJobOperations {
    private EmployeeRepo employeeRepo;
    private PrintJobRepo printJobRepo;

    public PrintJobOperations(EmployeeRepo employeeRepo, PrintJobRepo printJobRepo) {
        this.employeeRepo = employeeRepo;
        this.printJobRepo = printJobRepo;
    }

    public GenericResponse updatePrintJobStatus(Integer printId, PrintJobUpdateRequest dto) throws IdeaLabApiException {
        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Print Job Update Failed");
        if(dto.isValidStatus())
        {
            Employee employee = employeeRepo.findEmployeeById(dto.getEmployeeId());
            PrintJob printJob = printJobRepo.findPrintJobById(printId);
            if(employee != null && printJob != null) {
                printJob = printJobRepo.save(printJob);
                if(printJob.getStatus().getName().equalsIgnoreCase(dto.getStatus())) {
                    response.setSuccess(true);
                    response.setMessage("Print Job Updated");
                }
            }
        } else {
            ErrorType.PRINT_JOB_UPDATE_FAILED.throwException();
        }
        return response;

    }

    public GenericResponse deletePrintJobStatus(PrintJobDeleteRequest dto) {

        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Print Job Delete Failed");

        Employee employee = employeeRepo.findEmployeeById(dto.getEmployeeId());
        PrintJob printJob = printJobRepo.findPrintJobById(dto.getPrintJobId());

        if(employee != null && printJob != null) {
            printJobRepo.delete(printJob);

            response.setSuccess(true);
            response.setMessage("Print Job Deleted Successfully");
        }

        return response;
    }

    public GetAllPrintJobListResponse getAllPrintJobs() throws IdeaLabApiException {
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

        if(printJobResponses == null || printJobResponses.size() == 0){
            ErrorType.PRINT_JOBS_NOT_EXIST.throwException();
        }

        return new GetAllPrintJobListResponse(printJobResponses);
    }
}