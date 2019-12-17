package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.Status;

import java.util.Objects;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class EmailMessageUpdateRequest implements GenericRequest {

    private String status;
    private String emailMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailMessageUpdateRequest that = (EmailMessageUpdateRequest) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(emailMessage, that.emailMessage);
    }

    @Override
    public String toString() {
        return "PrintJobUpdateRequest{" +
                "status=" + status +
                ", message='" + emailMessage + '\'' +
                '}';
    }

    @Override
    public void validate() {
        if (this.getStatus() != null && !this.getStatus().trim().isEmpty()) {
            Status requestStatus = Status.fromValue(this.getStatus());
            if (requestStatus == null || !requestStatus.isValid()) {
                throw new IdeaLabApiException(VALIDATION_ERROR, "Status is invalid");
            }
        }
        if (this.getEmailMessage() == null || this.getEmailMessage() == "")
            throw new IdeaLabApiException(VALIDATION_ERROR, "Email message cannot be empty");
    }
}
