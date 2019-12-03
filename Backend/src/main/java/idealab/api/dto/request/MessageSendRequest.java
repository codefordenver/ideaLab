package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.Status;

import java.util.Objects;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class MessageSendRequest implements GenericRequest {

    private String email;
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String status) {
        this.email = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageSendRequest that = (MessageSendRequest) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(message, that.message);
    }

    @Override
    public String toString() {
        return "PrintJobUpdateRequest{" +
                "email=" + email +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public void validate() {
        if(this.getEmail() == null)
            throw new IdeaLabApiException(VALIDATION_ERROR, "Message cannot be empty");

        if(this.getMessage() == null)
            throw new IdeaLabApiException(VALIDATION_ERROR, "Message cannot be empty");
    }
}
