package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.Status;

import java.util.Objects;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class UpdatePrintJobPropertiesRequest implements GenericRequest {

    private Integer employeeId;
    private String colorType;
    private String comments;
    private String status;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdatePrintJobPropertiesRequest{" +
                "employeeId=" + employeeId +
                ", colorType='" + colorType + '\'' +
                ", comments='" + comments + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdatePrintJobPropertiesRequest that = (UpdatePrintJobPropertiesRequest) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(colorType, that.colorType) &&
                Objects.equals(comments, that.comments) &&
                Objects.equals(status, that.status);
    }


    @Override
    public void validate() {
        if(this.getStatus() != null && !this.getStatus().trim().isEmpty()) {
            Status requestStatus = Status.fromValue(this.getStatus());
            if (requestStatus == null || !requestStatus.isValid()) {
                throw new IdeaLabApiException(VALIDATION_ERROR, "Status is invalid");
            }
        }
        if(this.getColorType() != null && !this.getColorType().trim().isEmpty()) {
            // TODO W.E : need to determine acceptable colors
            if(this.getColorType().length() > 10) {
                throw new IdeaLabApiException(VALIDATION_ERROR, "Color name is too long");
            }
        }
        if(this.getComments() != null && !this.getComments().trim().isEmpty()) {
            if(this.getComments().length() > 200) {
                throw new IdeaLabApiException(VALIDATION_ERROR, "Comments need to be less than 200 characters");
            }
        }
    }
}
