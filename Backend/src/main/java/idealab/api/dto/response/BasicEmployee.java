package idealab.api.dto.response;

import java.util.Objects;

import idealab.api.model.EmployeeRole;

public class BasicEmployee {
    private String username;
    private EmployeeRole role;
    private String firstName;
    private String lastName;

    public BasicEmployee() {
    }

    public BasicEmployee(String username, EmployeeRole role, String firstName, String lastName) {
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public EmployeeRole getRole() {
        return this.role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BasicEmployee)) {
            return false;
        }
        BasicEmployee basicEmployee = (BasicEmployee) o;
        return Objects.equals(username, basicEmployee.username) && Objects.equals(role, basicEmployee.role) && Objects.equals(firstName, basicEmployee.firstName) && Objects.equals(lastName, basicEmployee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role, firstName, lastName);
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", role='" + getRole() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }


}