package idealab.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

import static idealab.api.model.Status.*;

public class PrintJobUpdateRequest {

    private Integer employeeId;
    private String fileName;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getfileName() {
      return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    //Currently needed for test classes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintJobUpdateRequest that = (PrintJobUpdateRequest) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(fileName, that.fileName);
    }

    @Override
    public String toString() {
        return "PrintJobUpdateRequest{" +
                "employeeId=" + employeeId +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
