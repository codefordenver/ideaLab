package idealab.api.dto.response;


import java.time.LocalDateTime;

public class GetAllPrintJobResponse {
    private Integer id;
    private String colorChoice;
    private String name;
    private LocalDateTime createdDate;
    private String email;
    private String status;
    private Integer rank;
    private String comments;
    private String dropboxLink;

    public GetAllPrintJobResponse(){}

    public GetAllPrintJobResponse(Integer id, String colorChoice, String email, String status,
                                  LocalDateTime createdDate, Integer rank, String name,
                                  String comments, String dropboxLink) {
        this.id = id;
        this.rank = rank;
        this.name = name;
        this.colorChoice = colorChoice;
        this.email = email;
        this.status = status;
        this.createdDate = createdDate;
        this.comments = comments;
        this.dropboxLink = dropboxLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorChoice() {
        return this.colorChoice;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime currentStatusCreatedAt) {
        this.createdDate = currentStatusCreatedAt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDropboxLink() {
        return dropboxLink;
    }

    public void setDropboxLink(String dropboxLink) {
        this.dropboxLink = dropboxLink;
    }
}
