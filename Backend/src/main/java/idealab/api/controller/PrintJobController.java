package idealab.api.controller;

import idealab.api.dto.response.GetPrintJobDataResponse;
import idealab.api.dto.request.PrintJobNewRequest;
import idealab.api.dto.request.PrintModelUpdateRequest;
import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.operations.PrintJobOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/printjobs")
public class PrintJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintJobController.class);

    private final PrintJobOperations printJobOperations;

    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

    @GetMapping
    public ResponseEntity<GetAllPrintJobListResponse> getAllPrintJobs(){
        GetAllPrintJobListResponse response = printJobOperations.getAllPrintJobs();

        if(response == null || response.getPrintJobs() == null || response.getPrintJobs().size() == 0){
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<?> printJobNew(@ModelAttribute PrintJobNewRequest model) {
        LOGGER.info("PrintJobNew request is:" + model.toString());
        GetPrintJobDataResponse response = printJobOperations.newPrintJob(model);

        if (response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Sample Endpoint to UPDATE the model
    @PutMapping("/{printId}/model")
    public ResponseEntity<?> printJobUpdateModel(@PathVariable("printId") Integer printId,
                                                  @ModelAttribute PrintModelUpdateRequest model) {

        LOGGER.info("PrintJobUpdateModel request is job:" + printId.toString() + "| model: " + model.toString());
        GetPrintJobDataResponse response = printJobOperations.updateModel(printId, model);

        if (response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Sample Endpoint to DELETE the model
    @DeleteMapping("/{printId}/model")
    public ResponseEntity<?> printJobDeleteModel(@PathVariable("printId") Integer printId) {
        LOGGER.info("PrintJobDeleteModel request is " + printId.toString());
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
