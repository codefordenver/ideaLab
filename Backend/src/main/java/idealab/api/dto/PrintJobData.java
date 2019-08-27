package idealab.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import idealab.api.model.ColorType;
import idealab.api.model.EmailHash;
import idealab.api.model.PrintModel;

public class PrintJobData extends GenericResponse {

  private boolean isSuccess;
  private String message;
  private List <PrintModel> data;

  public List<PrintModel> getData() {
    return data;
  }

  public void setData(List<PrintModel> data) {
    this.data = data;
  }
}