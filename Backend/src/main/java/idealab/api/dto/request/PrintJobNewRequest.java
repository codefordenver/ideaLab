package idealab.api.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class PrintJobNewRequest {

    private String customerFirstName;
    private String customerLastName;
    private String email;
    private String color;
    private String comments;
    private String employeeNotes;
    private MultipartFile file;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getEmployeeNotes() {
        return employeeNotes;
    }

    public void setEmployeeNotes(String employeeNotes) {
        this.employeeNotes = employeeNotes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    @Override
    public String toString() {
        return "PrintJobNewRequest{" +
                "customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", email='" + email + '\'' +
                ", color='" + color + '\'' +
                ", comments='" + comments + '\'' +
                ", employeeNotes='" + employeeNotes + '\'' +
                ", file=" + file.getOriginalFilename() +
                '}';
    }
}
