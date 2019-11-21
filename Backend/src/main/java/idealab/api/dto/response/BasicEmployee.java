package idealab.api.dto.response;

import idealab.api.model.EmployeeRole;

import java.util.Objects;

public class BasicEmployee {


    private String firstName;
    private String lastName;
    private String username;
    private EmployeeRole role;

    public BasicEmployee() {
    }

    public BasicEmployee(String firstName, String lastName, String username, EmployeeRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicEmployee that = (BasicEmployee) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(username, that.username) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, role);
    }

    @Override
    public String toString() {
        return "BasicEmployee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
