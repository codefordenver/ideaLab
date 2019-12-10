package idealab.api.service;

import idealab.api.dto.request.MailRequest;

public interface EmailService {
    void sendSimpleMessage(MailRequest mailRequest);
}
