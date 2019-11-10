package idealab.api.dto.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;

public class UserResponse extends GenericResponse {
    private List<Map<String,String>> data;

    public List<Map<String,String>> getData() {
        return data;
    }

    /**
     * This sets a subset of the employee class. It sets first name, last name, username, and role.
     */
    public void setSimpleData(List<Object[]> data) {
        List<Map<String,String>> fullList = new ArrayList<>();
        
        for (Object[] item : data) {
            Map<String,String> innerMap = new HashMap<>();
            innerMap.put("name", item[0] + " " + item[1] + " (" + item[2] + ")");
            innerMap.put("role", item[3].toString());
            fullList.add(innerMap);
        }

        this.data = fullList;
    }

    public UserResponse(){}

    public UserResponse(String message) {
        this.setMessage(message);
        this.setSuccess(false);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserResponse response = (UserResponse) o;
        return Objects.equals(data, response.data);
    }

}