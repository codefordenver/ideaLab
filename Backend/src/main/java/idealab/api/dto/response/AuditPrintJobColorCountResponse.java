package idealab.api.dto.response;

import idealab.api.dto.helper.PrintJobColorCount;

import java.util.List;
import java.util.Map;

public class AuditPrintJobColorCountResponse extends GenericResponse {

    private Map<String, List<PrintJobColorCount>> data;

    public Map<String, List<PrintJobColorCount>> getData() {
        return data;
    }

    public void setData(Map<String, List<PrintJobColorCount>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuditPrintJobColorCountResponse{" +
                "data=" + data +
                '}';
    }
}
