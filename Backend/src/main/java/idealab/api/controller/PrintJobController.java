package idealab.api.controller;

import idealab.api.dto.*;
import idealab.api.operations.PrintJobOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import sun.net.www.content.text.Generic;

@RestController
@RequestMapping("/api/printjobs")
public class PrintJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintJobController.class);

    private final PrintJobOperations printJobOperations;

    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

    @PostMapping
    public ResponseEntity<?> printJobNew(@ModelAttribute PrintJobNewRequest model) {
        PrintJobData response = printJobOperations.newPrintJob(model);

        if (response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Sample Endpoint to UPDATE the model
    @PutMapping("/{printId}/model")
    public ResponseEntity<?> printJobUpdateModel(@PathVariable("printId") Integer printId,
                                                  @ModelAttribute PrintModel model) {
        PrintJobData response = printJobOperations.updateModel(printId, model);

        if (response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Sample Endpoint to DELETE the model
    @DeleteMapping("/{printId}/model")
    public ResponseEntity<?> printJobUpdateModel(@PathVariable("printId") Integer printId) {
        GenericResponse response = printJobOperations.deleteModel(printId);

        if (response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{printId}/status")
    public ResponseEntity<?> printJobUpdateStatus(@PathVariable("printId") Integer printId,
            @RequestBody PrintJobUpdateRequest dto) {
        LOGGER.info("PrintJobUpdateStatus request is " + dto.toString());

        GenericResponse response = printJobOperations.updatePrintJobStatus(printId, dto);

        if (response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<?> printJobDelete(@RequestBody PrintJobDeleteRequest dto) {
        LOGGER.info("PrintJobDelete request is " + dto.toString());

        GenericResponse response = printJobOperations.deletePrintJobStatus(dto);

        if (response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
