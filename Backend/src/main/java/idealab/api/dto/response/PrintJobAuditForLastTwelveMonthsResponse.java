package idealab.api.dto.response;

import java.util.Map;

public class PrintJobAuditForLastTwelveMonthsResponse extends GenericResponse {
  private Map<String, Integer> data;

  public Map<String, Integer> getData() {
    return data;
  }

  public void setData(Map<String, Integer> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "PrintJobAuditForLastTwelveMonthsResponse {"
      +"data "+data+
    "}";
  }
}
