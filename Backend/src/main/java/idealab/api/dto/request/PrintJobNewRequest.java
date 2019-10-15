package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;
import org.springframework.web.multipart.MultipartFile;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class PrintJobNewRequest implements GenericRequest {

    private String customerFirstName;
    private String customerLastName;
    private String email;
    private String color;
    private String comments;
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
                ", file=" + file.getOriginalFilename() +
                '}';
    }

    @Override
    public void validate() {
        if(this.customerFirstName == null || this.customerFirstName.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Customer first name is invalid");
        if(this.customerLastName == null || this.customerLastName.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Customer last name is invalid");
        if(this.email == null || this.email.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Email is invalid");
        if(this.color == null || this.color.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Color is invalid");
        if(this.file == null || this.file.isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "File is invalid");
        if(!this.file.getOriginalFilename().toLowerCase().endsWith(".stl"))
            throw new IdeaLabApiException(VALIDATION_ERROR, "File must be .stl");
    }
}
