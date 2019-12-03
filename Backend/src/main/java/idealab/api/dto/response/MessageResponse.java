package idealab.api.dto.response;

import idealab.api.model.Message;
import org.springframework.http.HttpStatus;

import java.util.*;

public class MessageResponse extends GenericResponse {
    private Message data;

    public Message getData() {
        return data;
    }

    public void setData(Message data) {
        this.data = data;
    }

    public MessageResponse(){}

    public MessageResponse(String message) {
        this.setMessage(message);
        this.setSuccess(false);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MessageResponse response = (MessageResponse) o;
        return Objects.equals(data, response.data);
    }

}