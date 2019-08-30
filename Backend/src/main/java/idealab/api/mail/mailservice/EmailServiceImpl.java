package idealab.api.mail.mailservice;

import idealab.api.dto.GenericResponse;
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

    public GenericResponse sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            emailSender.send(message);
        } catch (Exception e) {
            return new GenericResponse(false, "Email Failed to Send");
        }
        return new GenericResponse(true, "Email Sent Successfully");
    }
}
