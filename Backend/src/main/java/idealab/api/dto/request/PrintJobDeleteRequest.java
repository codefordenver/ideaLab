package idealab.api.dto.request;

import java.util.Objects;

public class PrintJobDeleteRequest {

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
}
