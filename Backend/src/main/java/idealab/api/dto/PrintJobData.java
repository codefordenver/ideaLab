package idealab.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import idealab.api.model.ColorType;
import idealab.api.model.EmailHash;
import idealab.api.model.PrintModel;

public class PrintJobData {

  private boolean isSuccess;
  private String message;
  private List <PrintModel> data;

  public boolean isSuccess() {
    return isSuccess;
  }

  public void setSuccess(boolean success) {
    isSuccess = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "GenericResponse{" + "isSuccess=" + isSuccess + ", message='" + message + '\'' + '}';
  }

  public List<PrintModel> getData() {
    return data;
  }

  public void setData(List<PrintModel> data) {
    this.data = data;
  }
}