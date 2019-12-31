package idealab.api.dto.request;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

import java.util.Objects;

import idealab.api.exception.IdeaLabApiException;

/**
 * This class is used to update the email info request (change the email and password associated
 * with the app).
 */
public class UpdateEmailInfoRequest implements GenericRequest {

    private String email;
    private String password;

    public UpdateEmailInfoRequest() {
    }

    public UpdateEmailInfoRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UpdateEmailInfoRequest)) {
            return false;
        }
        UpdateEmailInfoRequest updateEmailInfoRequest = (UpdateEmailInfoRequest) o;
        return Objects.equals(email, updateEmailInfoRequest.email)
                && Objects.equals(password, updateEmailInfoRequest.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "{" + " email='" + getEmail() + "'" + ", password='" + getPassword() + "'" + "}";
    }

    @Override
    public void validate() {
        if (this.password == null || this.password.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Password is invalid");
        if (this.email == null || this.email.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Password is invalid");
        if (!this.email.contains(Character.toString('@')))
            throw new IdeaLabApiException(VALIDATION_ERROR, "The email must contain an @ symbol.");
    }

}
