package idealab.api.controller;

import idealab.api.dto.request.MessageUpdateRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.MessageResponse;
import idealab.api.operations.AuditOperations;
import idealab.api.operations.MessageOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final MessageOperations messageOperations;

    public MessageController(MessageOperations messageOperations) {
        this.messageOperations = messageOperations;
    }

    @GetMapping()
    public ResponseEntity<?> getMessageByStatusController(@RequestParam(required = true) String status) {
        MessageResponse response = messageOperations.getMessageByStatus(status);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

//    @PutMapping()
//    public ResponseEntity<?> updateMessageController(@RequestBody MessageUpdateRequest messageUpdateRequest) {
//        MessageResponse response = messageOperations.updateMessage(messageUpdateRequest);
//        return new ResponseEntity<>(response, response.getHttpStatus());
//    }

}
