package idealab.api.controller;

import idealab.api.dto.GenericResponse;
import idealab.api.dto.PrintJobNewRequest;
import idealab.api.dto.PrintJobUpdateRequest;
import idealab.api.operations.DropboxOperations;
import idealab.api.operations.PrintJobOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PrintJobController {

    private final PrintJobOperations printJobOperations;

    public PrintJobController(PrintJobOperations printJobOperations) {
        this.printJobOperations = printJobOperations;
    }

    @PostMapping("/api/printjob")
    public ResponseEntity<?> printJobNew(@ModelAttribute PrintJobNewRequest model) {
        // TODO Create new print job
        GenericResponse response = printJobOperations.newPrintJob(model);

        // TODO Read id of returned print job

        // TODO Save dropbox file w/ new ID
        int idNumber = 101;
        GenericResponse response2 = printJobOperations.newPrintJobFile(idNumber, model.getFile());
        if(response.isSuccess())
            return new ResponseEntity<>(response2, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(response2, HttpStatus.BAD_REQUEST);
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
