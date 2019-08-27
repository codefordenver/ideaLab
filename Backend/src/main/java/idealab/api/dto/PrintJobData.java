package idealab.api.dto;

import java.util.List;

import idealab.api.model.PrintJob;

public class PrintJobData extends GenericResponse {

  private boolean isSuccess;
  private String message;
  private List <PrintJob> data;

  public List<PrintJob> getData() {
    return data;
  }

  public void setData(List<PrintJob> data) {
    this.data = data;
  }
}