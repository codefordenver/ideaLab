package idealab.api.operations;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobDeleteRequest;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.model.Employee;
import idealab.api.model.PrintStatus;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintStatusRepo;
import org.springframework.stereotype.Component;

@Component
public class PrintJobOperations {

    private EmployeeRepo employeeRepo;
    private PrintStatusRepo printStatusRepo;

    public PrintJobOperations(EmployeeRepo employeeRepo, PrintStatusRepo printStatusRepo) {
        this.employeeRepo = employeeRepo;
        this.printStatusRepo = printStatusRepo;
    }

    public GenericResponse updatePrintJobStatus(Integer printId, PrintJobUpdateRequest dto)
    {
        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Print Job Status Update Failed");

        if(dto.isValidStatus())
        {
            Employee employee = employeeRepo.getEmployeeById(dto.getEmployeeId());
            PrintStatus printStatus = printStatusRepo.getPrintStatusById(printId);

            if(employee != null && printStatus != null) {
                printStatus = printStatusRepo.save(printStatus);
                if(printStatus.getStatus().getName().equalsIgnoreCase(dto.getStatus())) {
                    response.setSuccess(true);
                    response.setMessage("Print Job Status Updated");
                }
            }
        } else {
            response.setMessage("Print Job Status Update Failed - Invalid Status");
        }

        return response;

    }

    public GenericResponse deletePrintJobStatus(PrintJobDeleteRequest dto) {

        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Print Job Status Delete Failed");

        Employee employee = employeeRepo.getEmployeeById(dto.getEmployeeId());
        PrintStatus printStatus = printStatusRepo.getPrintStatusById(dto.getPrintStatusId());

        if(employee != null && printStatus != null) {
            printStatusRepo.delete(printStatus);

            response.setSuccess(true);
            response.setMessage("Print Job Status Deleted Successfully");
        }

        return response;
    }
}
