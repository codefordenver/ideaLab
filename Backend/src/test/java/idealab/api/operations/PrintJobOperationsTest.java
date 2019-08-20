package idealab.api.operations;

import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.request.PrintJobUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class PrintJobOperationsTest {

    private PrintJobOperations operations;

    @Before
    public void setup() {
        operations = new PrintJobOperations();
    }

    @Test
    public void updatePrintJobSuccess() {
        //Given
        PrintJobUpdateRequest request = new PrintJobUpdateRequest();
        request.setEmployeeId(1);
        request.setPrintStatusId(2);
        request.setStatus("Completed");

        //When
        GenericResponse response = operations.updatePrintJob(request);

        //Then
        assertTrue("response is not true", response.isSuccess() == true);
        assertTrue("print job was not updated", response.getMessage().equalsIgnoreCase("Print Job Updated"));
    }

    @Test
    public void updatePrintJobFail() {
        //Given
        PrintJobUpdateRequest request = new PrintJobUpdateRequest();
        request.setEmployeeId(1);
        request.setPrintStatusId(2);
        request.setStatus("asfdkjasdlkfjasdf");

        //When
        GenericResponse response = operations.updatePrintJob(request);

        //Then
        assertTrue("response is not false", response.isSuccess() == false);
        assertTrue("print job was updated", response.getMessage().equalsIgnoreCase("Invalid Status"));
    }

}
