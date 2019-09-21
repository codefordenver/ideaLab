package idealab.api.dto.request;

import java.util.Objects;

public class UserChangePasswordRequest {

    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public UserChangePasswordRequest() {
    }

    public UserChangePasswordRequest(String username, String oldPassword, String newPassword, String confirmNewPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
                ", oldPassword='" + oldPassword + '\'' +
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
                Objects.equals(oldPassword, that.oldPassword) &&
                Objects.equals(newPassword, that.newPassword) &&
                Objects.equals(confirmNewPassword, that.confirmNewPassword);
    }

}
