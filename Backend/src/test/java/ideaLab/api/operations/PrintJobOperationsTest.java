package idealab.api.operations;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.repositories.EmployeeRepo;
import idealab.api.repositories.PrintStatusRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class PrintJobOperationsTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private PrintStatusRepo printStatusRepo;

    private PrintJobOperations operations;

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

        Integer printStatusId = 3;

        //When
        GenericResponse response = operations.updatePrintJobStatus(printStatusId, request);

        //Then
        assertTrue("response is not true", response.isSuccess() == true);
        assertTrue("print job was not updated", response.getMessage().equalsIgnoreCase("Print Job Updated"));
    }

    @Test
    public void updatePrintJobFail() {
        //Given
        PrintJobUpdateRequest request = new PrintJobUpdateRequest();
        request.setEmployeeId(1);
        request.setStatus("asfdkjasdlkfjasdf");

        Integer printStatusId = 3;

        //When
        GenericResponse response = operations.updatePrintJobStatus(printStatusId, request);

        //Then
        assertTrue("response is not false", response.isSuccess() == false);
        assertTrue("print job was updated", response.getMessage().equalsIgnoreCase("Invalid Status"));
    }

}
