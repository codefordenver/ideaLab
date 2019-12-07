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
        HttpStatus.BAD_REQUEST
    ),

    PRINT_JOB_UPDATE_FAILED(
            5,
            "Print Job Update Failed",
            HttpStatus.BAD_REQUEST
    ),

    REQUEST_STATUS_IS_NOT_VALID(
            6,
            "Print Job Update Failed - Invalid Status",
            HttpStatus.BAD_REQUEST
    ),

    DROPBOX_UPLOAD_FILE_ERROR(
            7,
            "Error Creating New File",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    DROPBOX_DELETE_FILE_ERROR(
            8,
            "Error Deleting File",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
    DROPBOX_UPDATE_FILE_ERROR(
            9,
            "Error Updating File",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
    USER_NOT_FOUND(
            10,
            "User Not Found or There Are Not Any Users",
            HttpStatus.BAD_REQUEST
    ),
    VALIDATION_ERROR(
            11,
            "Validation Error",
            HttpStatus.BAD_REQUEST
    ),
    COLOR_CANT_FIND_BY_TYPE(
    		12,
    		"The Color Could Not Be Found or No Colors Exist.",
    		HttpStatus.NOT_FOUND
    ),
    MESSAGE_NOT_FOUND(
            13,
            "The requested message does not exist",
            HttpStatus.NOT_FOUND
    );

    ErrorType(int errorCode, String errorMessage, HttpStatus responseStatus){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.responseStatus = responseStatus;
    }

    private int errorCode;
    private String errorMessage;
    private HttpStatus responseStatus;

    public void throwException() throws RuntimeException{
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
