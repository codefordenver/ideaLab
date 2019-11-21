package idealab.api.dto.response;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import idealab.api.model.EmployeeRole;

public class BasicEmployeeResponse extends GenericResponse {
    private List<EmployeeBasic> data;

    public List<EmployeeBasic> getData() {
        return data;
    }

    public void setSimpleData(List<EmployeeBasic> data) {
        this.data = data;
    }

    public BasicEmployeeResponse(){}

    public BasicEmployeeResponse(String message) {
        this.setMessage(message);
        this.setSuccess(false);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BasicEmployeeResponse response = (BasicEmployeeResponse) o;
        return Objects.equals(data, response.data);
    }

    /**
     * Subset of the Employee model. Only contains id, username, role, first name, and last name.
     */
    public interface EmployeeBasic { 
        String getFirstName();
        String getLastName();
        String getUsername();
        EmployeeRole getRole();
    }

}