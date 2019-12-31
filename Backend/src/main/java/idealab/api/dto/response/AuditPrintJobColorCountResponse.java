package idealab.api.dto.response;

import java.util.Map;

public class AuditPrintJobColorCountResponse extends GenericResponse {

    private Map<String, Map<String,Integer>> data;

    public Map<String, Map<String,Integer>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String,Integer>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuditPrintJobColorCountResponse{" +
                "data=" + data +
                '}';
    }
}
