package idealab.api.controller;

import idealab.api.dto.request.MailRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private EmailService emailService;

    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody MailRequest mailRequest) {
        emailService.sendSimpleMessage(mailRequest);
        GenericResponse response = new GenericResponse();
        response.setSuccess(true);
        response.setMessage("Email sent successfully");
        response.setHttpStatus(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
