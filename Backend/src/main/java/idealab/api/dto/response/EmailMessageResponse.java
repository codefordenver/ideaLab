package idealab.api.dto.response;

import idealab.api.model.EmailMessage;
import org.springframework.http.HttpStatus;

import java.util.*;

public class EmailMessageResponse extends GenericResponse {
    private EmailMessage data;

    public EmailMessage getData() {
        return data;
    }

    public void setData(EmailMessage data) {
        this.data = data;
    }

    public EmailMessageResponse() {
    }

    public EmailMessageResponse(String message) {
        this.setMessage(message);
        this.setSuccess(false);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public EmailMessageResponse(EmailMessage emailMessage) {
        this.setMessage("Successfully returned the email message for requested status");
        this.setSuccess(true);
        this.setHttpStatus(HttpStatus.ACCEPTED);

        this.setData(emailMessage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmailMessageResponse response = (EmailMessageResponse) o;
        return Objects.equals(data.getId(), response.data.getId()) &&
                Objects.equals(data.getEmailMessage(), response.data.getEmailMessage()) &&
                Objects.equals(data.getStatus(), response.data.getStatus());
    }

}