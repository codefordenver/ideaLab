package idealab.api.controller;

import idealab.api.dto.request.*;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.operations.PrintJobOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/print-jobs")
public class PrintJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintJobController.class); // TODO: Logs should not be in controller

    private final PrintJobOperations printJobOperations;

    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

    @GetMapping
    public ResponseEntity<?> printJobGetAll(@RequestParam(required = false) String status) {
        LOGGER.info("Return all print jobs");
        PrintJobResponse response = printJobOperations.getAllPrintJobs(status);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // TODO W.E. : add query param or boolean to model to accept mass upload (ignore 5 file max)
    @PostMapping
    public ResponseEntity<?> printJobNew(@ModelAttribute PrintJobNewRequest model, Principal principal) {
        PrintJobResponse response = printJobOperations.newPrintJob(model, principal);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{print-id}")
    public ResponseEntity<?> printJobGetById(@PathVariable("print-id") Integer printId) {
        PrintJobResponse response = printJobOperations.getPrintJobById(printId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{print-id}/model")
    public ResponseEntity<?> printJobUpdateModel(@PathVariable("print-id") Integer printId,
                                                  @ModelAttribute PrintModelUpdateRequest model) {

        LOGGER.info("PrintJobUpdateModel request is job:" + printId.toString() + "| model: " + model.toString());
        PrintJobResponse response = printJobOperations.updateModel(printId, model);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{print-id}/model")
    public ResponseEntity<?> printJobDeleteModel(@PathVariable("print-id") Integer printId) {
        LOGGER.info("PrintJobDeleteModel request is " + printId.toString());
        GenericResponse response = printJobOperations.deleteModel(printId);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{print-id}/status")
    public ResponseEntity<?> printJobUpdateStatus(@PathVariable ("print-id") Integer printId,
                                                  @RequestBody PrintJobUpdateRequest dto){

        LOGGER.info("PrintJobUpdateStatus request is " + dto.toString());

        GenericResponse response = printJobOperations.updatePrintJobStatus(printId, dto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping
    public ResponseEntity<?> printJobDelete(@RequestBody PrintJobDeleteRequest dto)
    {
        LOGGER.info("PrintJobDelete request is " + dto.toString());

        GenericResponse response = printJobOperations.deletePrintJob(dto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/deletable")
    public ResponseEntity<?> getDeletablePrintJobs() {
        LOGGER.info("getDeletablePrintJobs ");

        PrintJobResponse response = printJobOperations.getDeletablePrintJobs();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    //Don't include the field in the JSON if you dont want it updated
    @PutMapping("/{print-id}")
    public ResponseEntity<?> updatePrintJobProperties(@PathVariable("print-id") Integer printId, @RequestBody UpdatePrintJobPropertiesRequest request, Principal principal) {
        PrintJobResponse response = printJobOperations.updatePrintJobProps(printId, request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
