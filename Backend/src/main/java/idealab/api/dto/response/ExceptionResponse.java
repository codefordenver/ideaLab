package idealab.api.dto.response;

import java.time.LocalDateTime;

public class ExceptionResponse { // TODO: Create a builder pattern that actually looks more pleasant.
    private int errorCode;
    private String errorName;
    private String errorMessage;
    private String errorDescription;    // this and the following is actually useful for us in the logging part. Looks like it's better if we don't come back in Response..
    private String exceptionClassName;  // this
    private LocalDateTime errorTime;


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

    public void setErrorTime(LocalDateTime errorTime){
        this.errorTime = errorTime;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorName() {
        return errorName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public LocalDateTime getErrorTime(){
        return errorTime;
    }
}
