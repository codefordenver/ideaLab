package idealab.api.exception;

public class IdeaLabApiException extends RuntimeException{

    private static final long serialVersionUID = -5477121430747808511L;
    private ErrorType errorType;

    public IdeaLabApiException(ErrorType errorType){
        super(errorType.getErrorMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType(){
        return errorType;
    }

}
