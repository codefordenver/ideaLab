package idealab.api.dto;

import static idealab.api.models.Status.*;

public class PrintJobUpdateRequest {

    private Integer employeeId;
    private String printStatusId;
    private String status;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getPrintStatusId() {
        return printStatusId;
    }

    public void setPrintStatusId(String printStatusId) {
        this.printStatusId = printStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isValidStatus() {
        if(this.status != null && !this.status.isEmpty()) {
            if(this.status.equalsIgnoreCase(ARCHIVED.toString()))
                return true;
            if(this.status.equalsIgnoreCase(PENDING_CUSTOMER_RESPONSE.toString()))
                return true;
            if(this.status.equalsIgnoreCase(PENDING_REVIEW.toString()))
                return true;
            if(this.status.equalsIgnoreCase(PRINTING.toString()))
                return true;
            if(this.status.equalsIgnoreCase(REJECTED.toString()))
                return true;
            if(this.status.equalsIgnoreCase(COMPLETED.toString()))
                return true;
            if(this.status.equalsIgnoreCase(FAILED.toString()))
                return true;
        }
        return false;
    }

}
