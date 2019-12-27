package idealab.api.dto.response;

import idealab.api.operations.AuditOperations;

import java.util.List;
import java.util.Map;

public class AuditPrintJobColorCountResponse extends GenericResponse {

    private Map<Integer, List<AuditOperations.PrintJobColorCount>> data;

    public Map<Integer, List<AuditOperations.PrintJobColorCount>> getData() {
        return data;
    }

    public void setData(Map<Integer, List<AuditOperations.PrintJobColorCount>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuditPrintJobColorCountResponse{" +
                "data=" + data +
                '}';
    }
}
