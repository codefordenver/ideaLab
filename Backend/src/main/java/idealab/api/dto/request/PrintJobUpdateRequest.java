package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;

import java.util.Objects;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class PrintJobUpdateRequest implements GenericRequest {

    private Integer employeeId;
    private String status;

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

    //Currently needed for test classes
    // I think it's better to achieve that without using this. It's bad practice to have a code which is only used for testing purposes ~ kaansonmezoz
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintJobUpdateRequest that = (PrintJobUpdateRequest) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public String toString() {
        return "PrintJobUpdateRequest{" +
                "employeeId=" + employeeId +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public void validate() {
        if(this.employeeId == null || this.employeeId < 0)
            throw new IdeaLabApiException(VALIDATION_ERROR, "Employee Id is invalid");
        if(this.status == null || this.status.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "Status is invalid");
    }
}
