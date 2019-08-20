package idealab.api.dto.response;

import java.util.List;

public class GetAllPrintJobListResponse {
    private List<GetAllPrintJobResponse> printJobs;

    public GetAllPrintJobListResponse(List<GetAllPrintJobResponse> printJobs){
        this.printJobs = printJobs;
    }

    public GetAllPrintJobListResponse(){}

    public List<GetAllPrintJobResponse> getPrintJobs() {
        return printJobs;
    }

    public void setPrintJobs(List<GetAllPrintJobResponse> printJobs) {
        this.printJobs = printJobs;
    }
}
