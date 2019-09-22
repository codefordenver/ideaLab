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

    public PrintJobResponse() {
        this.setSuccess(false);
        this.setMessage("Could not return print jobs");
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public PrintJobResponse(PrintJob printJob){
      this.setSuccess(true);
      this.setMessage("Successfully returned print job");
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