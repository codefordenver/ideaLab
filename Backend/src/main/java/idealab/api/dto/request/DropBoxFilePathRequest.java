package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

public class DropBoxFilePathRequest implements GenericRequest {

    private Integer printJobId;
    private String newPath;

    public Integer getPrintJobId() {
        return printJobId;
    }

    public void setPrintJobId(Integer printJobId) {
        this.printJobId = printJobId;
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    @Override
    public void validate() {
        if(this.printJobId == null || this.printJobId < 0)
            throw new IdeaLabApiException(VALIDATION_ERROR, "PrintJob Id is invalid");
        if(this.newPath == null || this.newPath.trim().isEmpty())
            throw new IdeaLabApiException(VALIDATION_ERROR, "New Path is invalid");
    }
}
