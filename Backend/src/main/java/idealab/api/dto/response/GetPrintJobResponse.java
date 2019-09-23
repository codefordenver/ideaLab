package idealab.api.dto.response;

import idealab.api.model.PrintJob;

import java.util.List;
import java.util.Objects;

public class GetPrintJobResponse extends GenericResponse {
  private List <PrintJob> data;

  public List<PrintJob> getData() {
    return data;
  }

  public void setData(List<PrintJob> data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    GetPrintJobResponse response = (GetPrintJobResponse) o;
    return Objects.equals(data, response.data);
  }

}