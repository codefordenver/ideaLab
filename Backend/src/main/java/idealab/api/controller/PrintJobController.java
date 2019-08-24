package idealab.api.controller;

import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.request.PrintJobUpdateRequest;
import idealab.api.dto.response.GetAllPrintJobListResponse;
import idealab.api.operations.PrintJobOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<GetAllPrintJobListResponse> getAllPrintJobs(){
        GetAllPrintJobListResponse response = printJobOperations.getAllPrintJobs();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
