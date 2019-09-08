package idealab.api.controller;

import idealab.api.dto.request.PrintJobDeleteRequest;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.operations.PrintJobOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/printjobs")
public class PrintJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintJobController.class);

    private final PrintJobOperations printJobOperations;

    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

    @PutMapping("/{printId}/status")
    public ResponseEntity<?> printJobUpdateStatus(@PathVariable ("printId") Integer printId, @RequestBody PrintJobUpdateRequest dto)
    {
        LOGGER.info("PrintJobUpdateStatus request is " + dto.toString());

        GenericResponse response = printJobOperations.updatePrintJobStatus(printId, dto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping
    public ResponseEntity<?> printJobDelete(@RequestBody PrintJobDeleteRequest dto)
    {
        LOGGER.info("PrintJobDelete request is " + dto.toString());

        GenericResponse response = printJobOperations.deletePrintJobStatus(dto);
        return new ResponseEntity<>(response, response.getHttpStatus());
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
}
