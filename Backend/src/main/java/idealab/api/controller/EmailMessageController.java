package idealab.api.controller;

import idealab.api.dto.request.EmailMessageUpdateRequest;
import idealab.api.dto.response.EmailMessageResponse;
import idealab.api.operations.EmailMessageOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class EmailMessageController {
    private final EmailMessageOperations emailMessageOperations;

    public EmailMessageController(EmailMessageOperations emailMessageOperations) {
        this.emailMessageOperations = emailMessageOperations;
    }

    @GetMapping()
    public ResponseEntity<?> getMessageByStatusController(@RequestParam(required = true) String status) {
        EmailMessageResponse response = emailMessageOperations.getMessageByStatus(status);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping()
    public ResponseEntity<?> updateMessageController(@RequestBody EmailMessageUpdateRequest emailMessageUpdateRequest) {
        EmailMessageResponse response = emailMessageOperations.updateMessage(emailMessageUpdateRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
