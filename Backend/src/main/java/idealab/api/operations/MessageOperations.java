package idealab.api.operations;

import idealab.api.dto.response.MessageResponse;
import idealab.api.exception.ErrorType;
import idealab.api.model.Status;
import org.springframework.stereotype.Service;


@Service
public class MessageOperations {
    public MessageOperations() {
    }

    public MessageResponse getMessageByStatus(){
        Status requestStatus = Status.fromValue(dto.getStatus());

        if(requestStatus == null || !requestStatus.isValid()){
            ErrorType.REQUEST_STATUS_IS_NOT_VALID.throwException();
        }
    }

    public MessageResponse updateMessage(){

    }
}
