package idealab.api.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.request.UpdatePrintJobPropertiesRequest;
import idealab.api.dto.response.DataResponse;
import idealab.api.dto.response.GenericResponse;
import idealab.api.model.PrintJob;
import idealab.api.operations.PrintJobOperations;

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
        DataResponse<PrintJob> response = printJobOperations.getAllPrintJobs(status);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // TODO W.E. : add query param or boolean to model to accept mass upload (ignore 5 file max)
    @PostMapping
    public ResponseEntity<?> printJobNew(@ModelAttribute PrintJobNewRequest model, Principal principal) {
        DataResponse<PrintJob> response = printJobOperations.newPrintJob(model, principal);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{print-id}")
    public ResponseEntity<?> printJobGetById(@PathVariable("print-id") Integer printId) {
        DataResponse<PrintJob> response = printJobOperations.getPrintJobById(printId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{print-id}/model")
    public ResponseEntity<?> printJobUpdateModel(@PathVariable("print-id") Integer printId,
                                                  @ModelAttribute PrintModelUpdateRequest model) {

        LOGGER.info("PrintJobUpdateModel request is job:" + printId.toString() + "| model: " + model.toString());
        DataResponse<PrintJob> response = printJobOperations.updateModel(printId, model);

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

        DataResponse<PrintJob> response = printJobOperations.getDeletablePrintJobs();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    //Don't include the field in the JSON if you dont want it updated
    @PutMapping("/{print-id}")
    public ResponseEntity<?> updatePrintJobProperties(@PathVariable("print-id") Integer printId, @RequestBody UpdatePrintJobPropertiesRequest request, Principal principal) {
        DataResponse<PrintJob> response = printJobOperations.updatePrintJobProps(printId, request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
