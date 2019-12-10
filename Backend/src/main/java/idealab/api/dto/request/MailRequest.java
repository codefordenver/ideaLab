package idealab.api.dto.request;

import idealab.api.exception.ErrorType;
import idealab.api.exception.IdeaLabApiException;

import java.util.Objects;

public class MailRequest implements GenericRequest {

    private String email;
    private String message;
    private String subject;

    public MailRequest() {
    }

    public MailRequest(String email, String message, String subject) {
        this.email = email;
        this.message = message;
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailRequest that = (MailRequest) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(message, that.message) &&
                Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, message, subject);
    }

    @Override
    public String toString() {
        return "MailRequest{" +
                "email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

    @Override
    public void validate() {
        if(email == null || email.isEmpty())
            throw new IdeaLabApiException(ErrorType.VALIDATION_ERROR, "Email is invalid");
        if(message == null || message.isEmpty())
            throw new IdeaLabApiException(ErrorType.VALIDATION_ERROR, "Message is invalid");
        if(subject == null || subject.isEmpty())
            throw new IdeaLabApiException(ErrorType.VALIDATION_ERROR, "Subject is invalid");
    }
}
