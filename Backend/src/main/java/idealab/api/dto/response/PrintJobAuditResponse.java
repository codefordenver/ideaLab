package idealab.api.dto.response;

import idealab.api.model.PrintJob;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PrintJobAuditResponse extends GenericResponse {
    private List<PrintJobAuditModel> data;

    public List<PrintJobAuditModel> getData() {
        return data;
    }

    public void setData(List<PrintJobAuditModel> data) {
        this.data = data;
    }

    public PrintJobAuditResponse(){}

    public PrintJobAuditResponse(String message) {
        this.setMessage(message);
        this.setSuccess(false);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public PrintJobAuditResponse(List<PrintJobAuditModel> printJobAuditModel) {
        this.setMessage("Successfully returned print job audit");
        this.setSuccess(true);
        this.setHttpStatus(HttpStatus.ACCEPTED);

        this.setData(printJobAuditModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PrintJobAuditResponse response = (PrintJobAuditResponse) o;
        return Objects.equals(data, response.data);
    }

}