package idealab.api.dto.responses;

public class ExceptionResponse {
    private int errorCode;
    private String errorName;
    private String errorMessage;
    private String errorDescription;
    private String exceptionClassName;

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }
}
