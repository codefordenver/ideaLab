package idealab.api.exception;

public class IdeaLabApiException extends RuntimeException{
    private ErrorType errorType;

    public IdeaLabApiException(ErrorType errorType){
        super(errorType.getErrorMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType(){
        return errorType;
    }

}
