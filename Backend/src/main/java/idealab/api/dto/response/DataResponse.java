package idealab.api.dto.response;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import idealab.api.model.RecordTimestamp;

public class DataResponse<T extends RecordTimestamp> extends GenericResponse {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public DataResponse(){}

    public DataResponse(String message) {
        this.setMessage(message);
        this.setSuccess(false);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public DataResponse(T setData) {
        this.setMessage("Successfully returned print job");
        this.setSuccess(true);
        this.setHttpStatus(HttpStatus.ACCEPTED);

        List<T> data = Arrays.asList(setData);
        this.setData(data);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DataResponse<T> response = (DataResponse<T>) o;
        return Objects.equals(data, response.data);
    }

}