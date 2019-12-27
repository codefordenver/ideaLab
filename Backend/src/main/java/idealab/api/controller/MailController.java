package idealab.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idealab.api.dto.request.MailRequest;
import idealab.api.dto.request.UpdateEmailInfoRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.operations.PropertyOperations;
import idealab.api.service.EmailService;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private EmailService emailService;
    private final PropertyOperations propertyOperations;

    public MailController(EmailService emailService, PropertyOperations propertyOperations) {
        this.emailService = emailService;
        this.propertyOperations = propertyOperations;
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

    @PostMapping("/info")
    /**
     * This updates the email and password associated with the application.
     * @param request
     * @return
     */
    public GenericResponse updateEmailInfo(@RequestBody UpdateEmailInfoRequest request) {
        String email = request.getEmail();
        String pw = request.getPassword();
        return propertyOperations.updateEmailInfo(email, pw);
    }

}
