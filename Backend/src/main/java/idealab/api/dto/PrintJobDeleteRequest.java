package idealab.api.dto;

import java.util.Objects;

public class PrintJobDeleteRequest {

    private Integer employeeId;
    private Integer printStatusId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getPrintStatusId() {
        return printStatusId;
    }

    public void setPrintStatusId(Integer printStatusId) {
        this.printStatusId = printStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintJobDeleteRequest that = (PrintJobDeleteRequest) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(printStatusId, that.printStatusId);
    }


    @Override
    public String toString() {
        return "PrintJobDeleteRequest{" +
                "employeeId=" + employeeId +
                ", printStatusId=" + printStatusId +
                '}';
    }
}
