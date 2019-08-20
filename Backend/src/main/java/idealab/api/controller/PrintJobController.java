package idealab.api.controller;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobDeleteRequest;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.operations.PrintJobOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/printjob")
public class PrintJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintJobController.class);

    private final PrintJobOperations printJobOperations;

    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

    @PutMapping("/status")
    public ResponseEntity<?> printJobUpdateStatus(@RequestBody PrintJobUpdateRequest dto)
    {
        LOGGER.info("PrintJobUpdateStatus request is " + dto.toString());

        GenericResponse response = printJobOperations.updatePrintJob(dto);

        if(response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<?> printJobDelete(@RequestBody PrintJobDeleteRequest dto)
    {
        LOGGER.info("PrintJobDelete request is " + dto.toString());

        GenericResponse response = printJobOperations.deletePrintJob(dto);

        if(response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
