package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.EmployeeRole;

import java.util.Objects;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class EmployeeUpdateRequest implements GenericRequest {

    private String username;
    private String role;

    public EmployeeUpdateRequest() {  }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserUpdate Request {" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeUpdateRequest that = (EmployeeUpdateRequest) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(role, that.role);
    }

    @Override
    public void validate() {
        if(this.username == null || username.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "The user id is not valid");
        if(this.role == null || EmployeeRole.fromValue(this.getRole()) == null)
            throw new IdeaLabApiException(VALIDATION_ERROR, "The role is not valid");
    }
}
