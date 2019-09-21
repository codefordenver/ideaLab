package idealab.api.controller;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.dto.response.GetPrintJobResponse;
import idealab.api.operations.PrintJobOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/printjobs")
public class PrintJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintJobController.class); // TODO: Logs should not be in controller

    private final PrintJobOperations printJobOperations;

    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

    @GetMapping
    public ResponseEntity<?> printJobGetAll() {
//        TODO: Add parameter that will find all and return based on status
        LOGGER.info("Return all print jobs");
        GetPrintJobResponse response = printJobOperations.getAllPrintJobs();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // TODO W.E. : add query param or boolean to model to accept mass upload (ignore 5 file max)
    @PostMapping
    public ResponseEntity<?> printJobNew(@ModelAttribute @Valid PrintJobNewRequest model) {
        GetPrintJobResponse response = printJobOperations.newPrintJob(model);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{printId}/model")
    public ResponseEntity<?> printJobUpdateModel(@PathVariable("printId") Integer printId,
                                                  @ModelAttribute PrintModelUpdateRequest model) {

        LOGGER.info("PrintJobUpdateModel request is job:" + printId.toString() + "| model: " + model.toString());
        GetPrintJobResponse response = printJobOperations.updateModel(printId, model);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{printId}/model")
    public ResponseEntity<?> printJobDeleteModel(@PathVariable("printId") Integer printId) {
        LOGGER.info("PrintJobDeleteModel request is " + printId.toString());
        GenericResponse response = printJobOperations.deleteModel(printId);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{printId}/status")
    public ResponseEntity<?> printJobUpdateStatus(@PathVariable ("printId") Integer printId,
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

    @GetMapping("deletable")
    public ResponseEntity<GetAllPrintJobListResponse> getDeletablePrintJobs() {
        LOGGER.info("getDeletablePrintJobs ");

        GetAllPrintJobListResponse response = printJobOperations.getDeletablePrintJobs();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
