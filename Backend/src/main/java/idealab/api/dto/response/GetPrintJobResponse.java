package idealab.api.dto.response;

import java.util.List;

import idealab.api.dto.response.GenericResponse;
import idealab.api.model.PrintJob;

public class GetPrintJobResponse extends GenericResponse {
  private List <PrintJob> data;

  public List<PrintJob> getData() {
    return data;
  }

  public void setData(List<PrintJob> data) {
    this.data = data;
  }
}