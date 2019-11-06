package idealab.api.dto.response;

import idealab.api.model.PrintJob;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PrintJobAuditResponse extends GenericResponse {
    private List<?> data;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public PrintJobAuditResponse(){}

    public PrintJobAuditResponse(String message) {
        this.setMessage(message);
        this.setSuccess(false);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public PrintJobAuditResponse(PrintJob printJob) {
        this.setMessage("Successfully returned print job");
        this.setSuccess(true);
        this.setHttpStatus(HttpStatus.ACCEPTED);

        List<PrintJob> printJobData = Arrays.asList(printJob);
        this.setData(printJobData);
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