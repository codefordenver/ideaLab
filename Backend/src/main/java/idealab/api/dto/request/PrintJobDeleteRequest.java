package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;

import java.util.Objects;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class PrintJobDeleteRequest implements GenericRequest {

    private Integer employeeId;
    private Integer printJobId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getPrintJobId() {
        return printJobId;
    }

    public void setPrintJobId(Integer printJobId) {
        this.printJobId = printJobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintJobDeleteRequest that = (PrintJobDeleteRequest) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(printJobId, that.printJobId);
    }


    @Override
    public String toString() {
        return "PrintJobDeleteRequest{" +
                "employeeId=" + employeeId +
                ", printJobId=" + printJobId +
                '}';
    }

    @Override
    public void validate() {
        if(this.employeeId == null || this.employeeId < 0)
            throw new IdeaLabApiException(VALIDATION_ERROR, "Employee Id is invalid");
        if(this.printJobId == null || this.printJobId < 0)
            throw new IdeaLabApiException(VALIDATION_ERROR, "Print Job Id is invalid");
    }
}