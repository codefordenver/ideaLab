package idealab.api.dto.response;


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
}
