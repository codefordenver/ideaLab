package idealab.api.operations;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.model.Employee;
import idealab.api.model.PrintStatus;
import idealab.api.model.Status;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintStatusRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class PrintJobOperationsTest {

    @InjectMocks
    private PrintJobOperations operations;

    @Mock
    PrintStatusRepo printStatusRepo;

    @Mock
    EmployeeRepo employeeRepo;

    @Before
    public void setup() {
        operations = new PrintJobOperations(employeeRepo, printStatusRepo);
    }

    @Test
    public void updatePrintJobSuccess() {
        //Given
        PrintJobUpdateRequest request = new PrintJobUpdateRequest();
        request.setEmployeeId(1);
        request.setStatus("Completed");

        Employee employee = new Employee();
        employee.setId(1);

        PrintStatus printStatus = new PrintStatus();
        printStatus.setEmployeeId(employee);
        printStatus.setId(2);
        printStatus.setStatus(Status.COMPLETED);

        //When
        when(employeeRepo.getEmployeeById(anyInt())).thenReturn(employee);
        when(printStatusRepo.getPrintStatusById(anyInt())).thenReturn(printStatus);
        when(printStatusRepo.save(printStatus)).thenReturn(printStatus);

        GenericResponse response = operations.updatePrintJobStatus(2, request);

        //Then
        assertTrue("response is not true", response.isSuccess() == true);
        assertTrue("print job was not updated", response.getMessage().equalsIgnoreCase("Print Job Status Updated"));
    }

    @Test
    public void updatePrintJobFail() {
        //Given
        PrintJobUpdateRequest request = new PrintJobUpdateRequest();
        request.setEmployeeId(1);
        request.setStatus("adfasfasf");

        Employee employee = new Employee();
        employee.setId(1);

        PrintStatus printStatus = new PrintStatus();
        printStatus.setEmployeeId(employee);
        printStatus.setId(2);
        printStatus.setStatus(Status.COMPLETED);

        //When
        when(employeeRepo.getEmployeeById(anyInt())).thenReturn(employee);
        when(printStatusRepo.getPrintStatusById(anyInt())).thenReturn(printStatus);

        GenericResponse response = operations.updatePrintJobStatus(2, request);

        //Then
        assertTrue("response is not false", response.isSuccess() == false);
        assertTrue("print job was updated", response.getMessage().equalsIgnoreCase("Print Job Status Update Failed - Invalid Status"));
    }

}
