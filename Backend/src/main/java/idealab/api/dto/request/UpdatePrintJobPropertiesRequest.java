package idealab.api.dto.request;

public class UpdatePrintJobPropertiesRequest implements GenericRequest {

    private String colorType;
    private String comments;
    private String status;

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void validate() {

    }
}
