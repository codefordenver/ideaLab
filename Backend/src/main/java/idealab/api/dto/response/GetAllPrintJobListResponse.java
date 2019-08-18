package idealab.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllPrintJobListResponse {
    private List<GetAllPrintJobResponse> printJobs;

    public GetAllPrintJobListResponse(List<GetAllPrintJobResponse> printJobs){
        this.printJobs = printJobs;
    }
}
