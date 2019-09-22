package idealab.api.dto.response;

import idealab.api.model.PrintJob;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PrintJobResponse extends GenericResponse {
    private List<PrintJob> data;

    public List<PrintJob> getData() {
        return data;
    }

    public void setData(List<PrintJob> data) {
        this.data = data;
    }

    public PrintJobResponse(){}
    
    public PrintJobResponse(String message) {
        this.setMessage(message);
        this.setSuccess(false);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public PrintJobResponse(PrintJob printJob) {
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
        PrintJobResponse response = (PrintJobResponse) o;
        return Objects.equals(data, response.data);
    }

}