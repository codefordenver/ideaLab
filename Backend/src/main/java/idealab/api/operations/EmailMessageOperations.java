package idealab.api.operations;

import idealab.api.dto.request.EmailMessageUpdateRequest;
import idealab.api.dto.response.EmailMessageResponse;
import idealab.api.exception.ErrorType;
import idealab.api.model.EmailMessage;
import idealab.api.model.Status;
import idealab.api.repositories.EmailMessageRepo;
import org.springframework.stereotype.Service;


@Service
public class EmailMessageOperations {
    private final EmailMessageRepo emailMessageRepo;

    public EmailMessageOperations(EmailMessageRepo emailMessageRepo) {
        this.emailMessageRepo = emailMessageRepo;
    }

    public EmailMessageResponse getMessageByStatus(String status){
        Status requestStatus = Status.fromValue(status);

        if(requestStatus == null || !requestStatus.isValid()){
            ErrorType.REQUEST_STATUS_IS_NOT_VALID.throwException();
        }

        EmailMessage emailMessage = emailMessageRepo.findEmailMessageByStatus(requestStatus);

        if(emailMessage == null || emailMessage.getEmailMessage() == null){
            ErrorType.MESSAGE_NOT_FOUND.throwException();
        }

        EmailMessageResponse response = new EmailMessageResponse(emailMessage);
        return response;
    }

    public EmailMessageResponse updateMessage(EmailMessageUpdateRequest req){
        req.validate();

        Status requestStatus = Status.fromValue(req.getStatus());
        EmailMessage emailMessage = emailMessageRepo.findEmailMessageByStatus(requestStatus);

        if(emailMessage == null){
            ErrorType.MESSAGE_NOT_FOUND.throwException();
        }

        emailMessage.setEmailMessage(req.getEmailMessage());
        emailMessageRepo.save(emailMessage);

        EmailMessageResponse response = new EmailMessageResponse(emailMessage);
        return response;
    }
}
