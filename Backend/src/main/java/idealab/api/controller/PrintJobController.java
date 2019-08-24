package idealab.api.controller;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobNewRequest;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.operations.PrintJobOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PrintJobController {

    private final PrintJobOperations printJobOperations;


    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

    @PostMapping("/api/printjob")
    public ResponseEntity<?> printJobNew(@ModelAttribute PrintJobNewRequest model) {
        // TODO Create new print job
        // ! Returning response for now until database is setup
        GenericResponse response = printJobOperations.newPrintJob(model);
        if(response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/printjob/status")
    public ResponseEntity<?> printJobUpdateStatus(@RequestBody PrintJobUpdateRequest dto)
    {
        GenericResponse response = printJobOperations.updatePrintJob(dto);
        if(response.isSuccess())
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    

}
