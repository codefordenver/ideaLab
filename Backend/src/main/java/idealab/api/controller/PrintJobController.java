package idealab.api.controller;

import idealab.api.dto.GenericResponse;
<<<<<<< HEAD
import idealab.api.dto.PrintJobData;
import idealab.api.dto.PrintJobNewRequest;
=======
import idealab.api.dto.PrintJobDeleteRequest;
>>>>>>> master
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.operations.PrintJobOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

=======
import org.springframework.web.bind.annotation.*;
>>>>>>> master

@RestController
@RequestMapping("/api/printjobs")
public class PrintJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintJobController.class);

    private final PrintJobOperations printJobOperations;

    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

<<<<<<< HEAD
    @PostMapping("/api/printjob")
    public ResponseEntity<?> printJobNew(@ModelAttribute PrintJobNewRequest model) {
        PrintJobData response = printJobOperations.newPrintJob(model);
=======
    @PutMapping("/{printId}/status")
    public ResponseEntity<?> printJobUpdateStatus(@PathVariable ("printId") Integer printId, @RequestBody PrintJobUpdateRequest dto)
    {
        LOGGER.info("PrintJobUpdateStatus request is " + dto.toString());

        GenericResponse response = printJobOperations.updatePrintJobStatus(printId, dto);

>>>>>>> master
        if(response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

<<<<<<< HEAD
    @PutMapping("/api/printjob/status")
    public ResponseEntity<?> printJobUpdateStatus(@RequestBody PrintJobUpdateRequest dto)
=======
    @DeleteMapping
    public ResponseEntity<?> printJobDelete(@RequestBody PrintJobDeleteRequest dto)
>>>>>>> master
    {
        LOGGER.info("PrintJobDelete request is " + dto.toString());

        GenericResponse response = printJobOperations.deletePrintJobStatus(dto);

        if(response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    

}
