package idealab.api.service;

import idealab.api.dto.request.MailRequest;
import idealab.api.exception.ErrorType;
import idealab.api.exception.IdeaLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Qualifier("getJavaMailSender") //WebMail.java
    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleMessage(MailRequest mailRequest) {
        mailRequest.validate();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailRequest.getEmail());
        message.setSubject(mailRequest.getSubject());
        message.setText(mailRequest.getMessage());
        try {
            emailSender.send(message);
        } catch (Exception e) {
            throw new IdeaLabApiException(ErrorType.GENERAL_ERROR, "Email failed to send");
        }
    }

}
