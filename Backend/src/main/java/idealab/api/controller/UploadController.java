package idealab.api.controller;

import idealab.api.dto.response.GenericResponse;
import idealab.api.operations.UploadOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private final UploadOperations uploadOperations;

    public UploadController(UploadOperations uploadOperations) {
        this.uploadOperations = uploadOperations;
    }

    @PostMapping("/user-csv")
    public ResponseEntity<?> uploadPrintJobCsv(@RequestParam("file") MultipartFile file) {
        GenericResponse response = uploadOperations.processUserCsvUpload(file);
        return new ResponseEntity<>(response, response.getHttpStatus());

    }
}
