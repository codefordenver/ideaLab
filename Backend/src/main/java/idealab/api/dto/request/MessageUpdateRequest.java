package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.Status;

import java.util.Objects;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class MessageUpdateRequest implements GenericRequest {

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        MessageUpdateRequest that = (MessageUpdateRequest) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(message, that.message);
    }

    @Override
    public String toString() {
        return "PrintJobUpdateRequest{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public void validate() {
        if(this.getStatus() != null && !this.getStatus().trim().isEmpty()) {
            Status requestStatus = Status.fromValue(this.getStatus());
            if (requestStatus == null || !requestStatus.isValid()) {
                throw new IdeaLabApiException(VALIDATION_ERROR, "Status is invalid");
            }
        }
        if(this.getMessage() == null)
            throw new IdeaLabApiException(VALIDATION_ERROR, "Message cannot be empty");
    }
}
