package idealab.api.controller;

import idealab.api.dto.GenericResponse;
import idealab.api.mail.mailservice.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class TestMailController {

    private EmailService emailService;

    public TestMailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public GenericResponse getMail() {
        String to = "test@test.com";
        String message = "This is a message test";
        String subject = "SubjectTest";

        return emailService.sendSimpleMessage(to, subject, message);
    }

}
