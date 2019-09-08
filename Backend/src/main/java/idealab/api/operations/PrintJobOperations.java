package idealab.api.operations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetAllPrintJobResponse;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;

@Component
public class PrintJobOperations {

    private EmployeeRepo employeeRepo;
    private PrintJobRepo printJobRepo;

    public PrintJobOperations(EmployeeRepo employeeRepo, PrintJobRepo printJobRepo) {
        this.employeeRepo = employeeRepo;
        this.printJobRepo = printJobRepo;
    }

    public GenericResponse updatePrintJobStatus(Integer printId, PrintJobUpdateRequest dto)
    {
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

        if(employee != null && printJob != null) {
            printJobRepo.delete(printJob);

            response.setSuccess(true);
            response.setMessage("Print Job Deleted Successfully");
        }

        return response;
    }

    public GetAllPrintJobListResponse getAllPrintJobs(){
        Iterable<PrintJob> allJobs = printJobRepo.findAll();
        List<GetAllPrintJobResponse> printJobResponses = new ArrayList<GetAllPrintJobResponse>();

        allJobs.forEach(printJob -> {
            Integer id = printJob.getId();
            String colorChoice = printJob.getColorTypeId().getColor();
            String email;
            String name;

            if(printJob.getEmailHashId().getCustomerInfo() != null) {
                email = printJob.getEmailHashId().getCustomerInfo().getEmail();
                name = printJob.getEmailHashId().getCustomerInfo().getFullName();
            } else {
                email = "No longer available";
                name = "No longer available";
            }

            String comments = printJob.getComments();
            String dropboxLink = printJob.getDropboxLink();
            String status = printJob.getStatus().getName();
            LocalDateTime createdDate = LocalDateTime.now();//printJob.getCreatedAt();
            Integer rank = printJob.getQueueId().getRank();

            
            


            GetAllPrintJobResponse printJobResponse =
                new GetAllPrintJobResponse(id, colorChoice, email, status, createdDate, rank, name, comments, dropboxLink);
            printJobResponses.add(printJobResponse);
        });

        return new GetAllPrintJobListResponse(printJobResponses);
    }

}
