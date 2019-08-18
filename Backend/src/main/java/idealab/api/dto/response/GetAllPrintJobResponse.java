package idealab.api.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetAllPrintJobResponse {
    private Integer id;
    private String colorChoice;
    private String email;
    private String status;
    private LocalDateTime currentStatusCreatedAt;
    private LocalDateTime initialRequestTime;
    private List<String> comments;
    private String dropboxLink;
}
