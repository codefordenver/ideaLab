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

        //Prep for when we get data models merged to master
        if(dto.isValidStatus())
        {
            //check if employee id is valid
            Employee employee = employeeRepo.getEmployeeById(dto.getEmployeeId());

            //check if print id is valid
            PrintStatus printStatus = printStatusRepo.getPrintStatusById(printId);

            if(employee != null && printStatus != null) {
                //Update print status
                printStatus = printStatusRepo.save(printStatus);

                if(printStatus.getStatus().getName().equalsIgnoreCase(dto.getStatus())) {
                    //return success message
                    response.setSuccess(true);
                    response.setMessage("Print Job Status Updated");
                }
            }
        } else {
            response.setMessage("Print Job Status Update Failed - Invalid Status");
        }

        return response;

    }

    public GenericResponse deletePrintJob(PrintJobDeleteRequest dto) {

        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Print Job Delete Failed");

        //check if employee id is valid
        Employee employee = employeeRepo.getEmployeeById(dto.getEmployeeId());

        //check if print id is valid
        PrintStatus printStatus = printStatusRepo.getPrintStatusById(dto.getPrintStatusId());

        if(employee != null && printStatus != null) {
            //delete print status
            printStatusRepo.delete(printStatus);

            //return success message
            response.setSuccess(true);
            response.setMessage("Deleted Successfully");
        }

        return response;
    }
}
