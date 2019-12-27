package idealab.api.dto.response;

import idealab.api.dto.helper.PrintJobColorCount;

import java.util.List;
import java.util.Map;

public class AuditPrintJobColorCountResponse extends GenericResponse {

    private Map<Integer, List<PrintJobColorCount>> data;

    public Map<Integer, List<PrintJobColorCount>> getData() {
        return data;
    }

    public void setData(Map<Integer, List<PrintJobColorCount>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuditPrintJobColorCountResponse{" +
                "data=" + data +
                '}';
    }
}
