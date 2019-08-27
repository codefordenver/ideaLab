package idealab.api.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    GENERAL_ERROR(
        1,
        "Oops something went wrong !",
        HttpStatus.INTERNAL_SERVER_ERROR
    ),

    PRINT_JOB_CANT_FIND_BY_ID(
            2,
            "Print job couldn't be found. Check your print job id !",
            HttpStatus.NOT_FOUND
    ),

    PRINT_JOBS_NOT_EXIST(
            3,
            "Print jobs not exist !",
            HttpStatus.NOT_FOUND
    ),

    PRINT_JOB_CANT_DELETED(
        4,
        "Print job couldn't be deleted !",
        HttpStatus.INTERNAL_SERVER_ERROR
    ),

    PRINT_JOB_UPDATE_FAILED(
            5,
            "Print Job Update Failed - Invalid Status",
            HttpStatus.INTERNAL_SERVER_ERROR
    );

    ErrorType(int errorCode, String errorMessage, HttpStatus responseStatus){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.responseStatus = responseStatus;
    }

    private int errorCode;
    private String errorMessage;
    private HttpStatus responseStatus;

    public void throwException() throws IdeaLabApiException {
        throw new IdeaLabApiException(this);
    }

    public int getErrorCode(){
        return errorCode;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public HttpStatus getResponseStatus(){
        return responseStatus;
    }
}
