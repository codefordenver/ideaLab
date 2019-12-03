package idealab.api.controller;

import idealab.api.dto.request.MessageUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.MessageResponse;
import idealab.api.operations.AuditOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final AuditOperations auditOperations;

    public MessageController(AuditOperations auditOperations) {
        this.auditOperations = auditOperations;
    }

    @GetMapping()
    public ResponseEntity<?> getAllPrintJobsAuditTable(@RequestParam(required = true) String status) {
        MessageResponse response = auditOperations.getMessageByStatus(status);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping()
    public ResponseEntity<?> getAllPrintJobsAuditTable(@RequestBody MessageUpdateRequest messageUpdateRequest) {
        MessageResponse response = auditOperations.updateMessage(messageUpdateRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
