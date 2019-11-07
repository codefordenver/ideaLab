package idealab.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.PrintJobAuditResponse;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;

public class TestUtil {

    public static GenericResponse stringToGenericResponse(String s) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            GenericResponse response = mapper.readValue(s, GenericResponse.class);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrintJobAuditResponse stringToPrintJobAuditResponse(String s) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PrintJobAuditResponse response = mapper.readValue(s, PrintJobAuditResponse.class);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
