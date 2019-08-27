package idealab.api.dto.responses;


import java.time.LocalDateTime;
import java.util.List;

public class GetAllPrintJobResponse {
    private Integer id;
    private String colorChoice;
    private String email;
    private String status;
    private LocalDateTime currentStatusCreatedAt;
    private LocalDateTime initialRequestTime;
    private List<String> comments;
    private String dropboxLink;

    public GetAllPrintJobResponse(){}

    public GetAllPrintJobResponse(Integer id, String colorChoice, String email, String status,
                                  LocalDateTime currentStatusCreatedAt, LocalDateTime initialRequestTime,
                                  List<String> comments, String dropboxLink) {
        this.id = id;
        this.colorChoice = colorChoice;
        this.email = email;
        this.status = status;
        this.currentStatusCreatedAt = currentStatusCreatedAt;
        this.initialRequestTime = initialRequestTime;
        this.comments = comments;
        this.dropboxLink = dropboxLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColorChoice() {
        return colorChoice;
    }

    public void setColorChoice(String colorChoice) {
        this.colorChoice = colorChoice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCurrentStatusCreatedAt() {
        return currentStatusCreatedAt;
    }

    public void setCurrentStatusCreatedAt(LocalDateTime currentStatusCreatedAt) {
        this.currentStatusCreatedAt = currentStatusCreatedAt;
    }

    public LocalDateTime getInitialRequestTime() {
        return initialRequestTime;
    }

    public void setInitialRequestTime(LocalDateTime initialRequestTime) {
        this.initialRequestTime = initialRequestTime;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getDropboxLink() {
        return dropboxLink;
    }

    public void setDropboxLink(String dropboxLink) {
        this.dropboxLink = dropboxLink;
    }
}
