package idealab.api.operations;

import idealab.api.dto.request.MessageUpdateRequest;
import idealab.api.dto.response.MessageResponse;
import idealab.api.exception.ErrorType;
import idealab.api.model.Message;
import idealab.api.model.Status;
import idealab.api.repositories.MessageRepo;
import org.springframework.stereotype.Service;


@Service
public class MessageOperations {
    private final MessageRepo messageRepo;

    public MessageOperations(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public MessageResponse getMessageByStatus(String status){
        Status requestStatus = Status.fromValue(status);

        if(requestStatus == null || !requestStatus.isValid()){
            ErrorType.REQUEST_STATUS_IS_NOT_VALID.throwException();
        }

        Message message = messageRepo.findMessageByStatus(requestStatus);

        if(message == null || message.getMessage() == null){
            ErrorType.MESSAGE_NOT_FOUND.throwException();
        }

        MessageResponse response = new MessageResponse(message);
        return response;
    }

//    public MessageResponse updateMessage(MessageUpdateRequest messageUpdateRequest){
//
//
//    }
}
