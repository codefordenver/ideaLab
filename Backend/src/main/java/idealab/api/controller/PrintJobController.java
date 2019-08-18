package idealab.api.controller;

import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.operations.PrintJobOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/printjob")
public class PrintJobController {

    private final PrintJobOperations printJobOperations;

    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

    @PutMapping("/status")
    public ResponseEntity<?> printJobUpdateStatus(@RequestBody PrintJobUpdateRequest dto)
    {
        GenericResponse response = printJobOperations.updatePrintJob(dto);
        if(response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAllPrintJobs(){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
