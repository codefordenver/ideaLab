package idealab.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class GenericResponse {

    private boolean isSuccess;
    private String message;

    @JsonIgnore
    private HttpStatus httpStatus;

    public GenericResponse() {
    }

    public GenericResponse(boolean isSuccess, String message, HttpStatus httpStatus) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "isSuccess=" + isSuccess +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericResponse that = (GenericResponse) o;
        return isSuccess == that.isSuccess &&
                Objects.equals(message, that.message);
    }

}