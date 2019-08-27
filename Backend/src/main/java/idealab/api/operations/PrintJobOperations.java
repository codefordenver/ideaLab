package idealab.api.operations;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobDeleteRequest;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.model.Employee;
import idealab.api.model.PrintJob;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintJobRepo;
import org.springframework.stereotype.Component;

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
            PrintJob printStatus = printJobRepo.findPrintJobById(printId);

            if(employee != null && printStatus != null) {
                printStatus = printJobRepo.save(printStatus);
                if(printStatus.getStatus().getName().equalsIgnoreCase(dto.getStatus())) {
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
        PrintJob printJob = printJobRepo.findPrintJobById(dto.getPrintStatusId());

        if(employee != null && printJob != null) {
            printJobRepo.delete(printJob);

            response.setSuccess(true);
            response.setMessage("Print Job Deleted Successfully");
        }

        return response;
    }
}
