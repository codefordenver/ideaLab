package idealab.api.mail.mailservice;

import idealab.api.dto.GenericResponse;

public interface EmailService {
    GenericResponse sendSimpleMessage(String to, String subject, String text);
}
