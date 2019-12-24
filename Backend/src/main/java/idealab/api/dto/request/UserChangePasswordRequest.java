package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;

import java.util.Objects;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class UserChangePasswordRequest implements GenericRequest {

    private String username;
    private String newPassword;
    private String confirmNewPassword;

    public UserChangePasswordRequest() {  }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public String toString() {
        return "UserChangePasswordRequest{" +
                "username='" + username + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChangePasswordRequest that = (UserChangePasswordRequest) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(newPassword, that.newPassword) &&
                Objects.equals(confirmNewPassword, that.confirmNewPassword);
    }

    @Override
    public void validate() {
        if(this.username == null || this.username.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Username is not valid");
        if(this.newPassword == null || this.newPassword.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "New password is not valid");
        if(this.confirmNewPassword == null || this.confirmNewPassword.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Confirm new password is not valid");
        if(!this.newPassword.equals(this.confirmNewPassword))
            throw new IdeaLabApiException(VALIDATION_ERROR, "New password and confirm new password are not equal");
    }
}
