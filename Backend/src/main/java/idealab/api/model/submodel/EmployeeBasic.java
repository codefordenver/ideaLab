package idealab.api.model.submodel;

import org.hibernate.validator.constraints.Length;

import idealab.api.model.EmployeeRole;

public class EmployeeBasic {

    private Integer id;
    private String username;
    private EmployeeRole role;
    private String firstName;
    private String lastName;

    public EmployeeBasic() {
    }

    public EmployeeBasic(String username, EmployeeRole role, String firstName, String lastName) {
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //getter & setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}