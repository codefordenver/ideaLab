package idealab.api.operations;

import idealab.api.dto.requests.PrintJobDeleteRequest;
import idealab.api.dto.requests.PrintJobUpdateRequest;
import idealab.api.dto.responses.GenericResponse;
import idealab.api.dto.responses.GetAllPrintJobListResponse;
import idealab.api.dto.responses.GetAllPrintJobResponse;
import idealab.api.exception.ErrorType;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PrintJobOperations {
    private EmployeeRepo employeeRepo;
    private PrintJobRepo printJobRepo;

    public PrintJobOperations(EmployeeRepo employeeRepo, PrintJobRepo printJobRepo) {
        this.employeeRepo = employeeRepo;
        this.printJobRepo = printJobRepo;
    }

    public GenericResponse updatePrintJobStatus(Integer printId, PrintJobUpdateRequest dto) {
        GenericResponse response = new GenericResponse();

        if(!dto.isValidStatus()){
            ErrorType.REQUEST_STATUS_IS_NOT_VALID.throwException();
        }

        Employee employee = employeeRepo.findEmployeeById(dto.getEmployeeId());
        PrintJob printJob = printJobRepo.findPrintJobById(printId);

        if(employee == null || printJob == null){
            ErrorType.PRINT_JOB_UPDATE_FAILED.throwException();
        }

        printJob = printJobRepo.save(printJob);

        if(printJob.getStatus().getName().equalsIgnoreCase(dto.getStatus())) {
            response.setSuccess(true);
            response.setMessage("Print Job Updated");
        }

        return response;
    }

    public GenericResponse deletePrintJobStatus(PrintJobDeleteRequest dto) {
        Employee employee = employeeRepo.findEmployeeById(dto.getEmployeeId());
        PrintJob printJob = printJobRepo.findPrintJobById(dto.getPrintJobId());

        if(employee == null || printJob == null){
            ErrorType.PRINT_JOB_CANT_DELETED.throwException();
        }

        printJobRepo.delete(printJob);

        GenericResponse response = new GenericResponse();

        response.setSuccess(true);
        response.setMessage("Print Job Deleted Successfully");

        return response;
    }

    public GetAllPrintJobListResponse getAllPrintJobs() {
        Optional<List<PrintJob>> printJobs = printJobRepo.findAllPrintJob();

        if(!printJobs.isPresent() || printJobs.get().size() == 0){
            ErrorType.PRINT_JOBS_NOT_EXIST.throwException();
        }

        List<GetAllPrintJobResponse> printJobResponses = printJobs.get().stream()
                .map(GetAllPrintJobResponse::new).collect(Collectors.toList());

        return new GetAllPrintJobListResponse(printJobResponses);
    }
}