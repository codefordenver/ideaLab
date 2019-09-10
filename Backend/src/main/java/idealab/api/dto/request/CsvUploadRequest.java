package idealab.api.dto.request;

import java.util.Objects;

public class CsvUploadRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String color;
    private Integer employeeId;
    private String status;

    public CsvUploadRequest(){}

    public CsvUploadRequest(String firstName, String lastName, String email, String color, Integer employeeId, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.color = color;
        this.employeeId = employeeId;
        this.status = status;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CsvUploadRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", color='" + color + '\'' +
                ", employeeId=" + employeeId +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CsvUploadRequest that = (CsvUploadRequest) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(color, that.color) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(status, that.status);
    }
}
