package idealab.api.exception;

public class IdeaLabApiException extends Exception{
    private ErrorType errorType;

    public IdeaLabApiException(ErrorType errorType){
        super(errorType.getErrorMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType(){
        return errorType;
    }

}
