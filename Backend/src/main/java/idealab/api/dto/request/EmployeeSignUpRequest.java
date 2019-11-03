package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.EmployeeRole;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class EmployeeSignUpRequest implements GenericRequest {

    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;

    public EmployeeSignUpRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "EmployeeSignUpRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public void validate() {
        if(username == null || username.trim().isEmpty()) {
            throw new IdeaLabApiException(VALIDATION_ERROR, "username is invalid");
        }
        if(password == null || username.trim().isEmpty()) {
            throw new IdeaLabApiException(VALIDATION_ERROR, "password is invalid");
        }
        if(role == null || EmployeeRole.fromString(role) == null) {
            throw new IdeaLabApiException(VALIDATION_ERROR, "role is invalid");
        }
        if(firstName == null || firstName.trim().isEmpty()) {
            throw new IdeaLabApiException(VALIDATION_ERROR, "firstName is invalid");
        }
        if(lastName == null || lastName.trim().isEmpty()) {
            throw new IdeaLabApiException(VALIDATION_ERROR, "lastName is invalid");
        }
    }
}
