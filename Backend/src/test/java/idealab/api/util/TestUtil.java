package idealab.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import idealab.api.dto.GenericResponse;

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
}
