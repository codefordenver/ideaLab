package idealab.api.dto.responses;


import idealab.api.model.PrintJob;

import java.time.LocalDateTime;

public class GetAllPrintJobResponse {
    private Integer id;
    private String colorChoice;
    private String email;
    private String status;
    private LocalDateTime currentStatusCreatedAt;
    private LocalDateTime initialRequestTime;
    private String comments;
    private String dropboxLink;

    public GetAllPrintJobResponse(PrintJob printJob){
        this.id = printJob.getId();
        this.colorChoice = printJob.getColorTypeId().getColor();
        this.email = printJob.getEmailHashId().getEmailHash();
        this.status = printJob.getStatus().getName();
        this.currentStatusCreatedAt = printJob.getUpdatedAt();
        this.initialRequestTime = printJob.getCreatedAt();
        this.comments = printJob.getComments();
        this.dropboxLink = printJob.getDropboxLink();
    }

    public GetAllPrintJobResponse(Integer id, String colorChoice, String email, String status,
                                  LocalDateTime currentStatusCreatedAt, LocalDateTime initialRequestTime,
                                  String comments, String dropboxLink) {
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

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCurrentStatusCreatedAt() {
        return currentStatusCreatedAt;
    }

    public LocalDateTime getInitialRequestTime() {
        return initialRequestTime;
    }

    public String getComments() {
        return comments;
    }

    public String getDropboxLink() {
        return dropboxLink;
    }
}
