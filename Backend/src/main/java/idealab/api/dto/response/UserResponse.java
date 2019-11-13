package idealab.api.dto.response;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import idealab.api.model.submodel.EmployeeBasic;

public class UserResponse extends GenericResponse {
    private List<EmployeeBasic> data;

    public List<EmployeeBasic> getData() {
        return data;
    }

    public void setSimpleData(List<EmployeeBasic> data) {
        this.data = data;
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