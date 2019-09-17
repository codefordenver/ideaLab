package idealab.api.dto.response;

import java.time.LocalDateTime;

public class ExceptionResponse { // Bunun icin bir builder pattern olusturabiliriz aslinda daha hos durur.
    private int errorCode;
    private String errorName;
    private String errorMessage;
    private String errorDescription;    // bu ve asagidaki aslında loglama kısmında bizlerin isine yarar. Response'da dönmesek sanki daha iyi gibi.
    private String exceptionClassName;  // bu
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
