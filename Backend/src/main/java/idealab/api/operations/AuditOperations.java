package idealab.api.operations;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import idealab.api.dto.helper.PrintJobColorCount;
import idealab.api.dto.response.AuditPrintJobColorCountResponse;
import idealab.api.dto.response.PrintJobAuditModel;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.model.Status;
import idealab.api.service.AuditService;

@Service
public class AuditOperations {
    private final AuditService auditService;

    public AuditOperations(AuditService auditService) {
        this.auditService = auditService;
    }

    public PrintJobAuditResponse allPrintJobsAudit(){
        AuditQuery query = auditService.getPrintJobAuditQuery();
        query.addOrder(AuditEntity.revisionNumber().desc());

        List<PrintJobAuditModel> auditList = auditService.processPrintJobAuditQuery(query);
        return new PrintJobAuditResponse(auditList);
    }

    public PrintJobAuditResponse printJobAuditById(Integer printJobId){
        AuditQuery query = auditService.getPrintJobAuditQuery();
        query.addOrder(AuditEntity.revisionNumber().desc());

        //add method allows filtering, this is the only difference between getting all print jobs
        query.add(AuditEntity.property("id").eq(printJobId));

        List<PrintJobAuditModel> auditList = auditService.processPrintJobAuditQuery(query);
        return new PrintJobAuditResponse(auditList);
    }

    public AuditPrintJobColorCountResponse printJobAuditColorsYearly() {
        AuditQuery query = auditService.getPrintJobAuditQuery();
        query.addOrder(AuditEntity.revisionNumber().desc());
        query.add(AuditEntity.property("status").eq(Status.COMPLETED));

        LocalDateTime x = LocalDateTime.now().minusMonths(12);
        Timestamp startDate = Timestamp.valueOf(x);
        Long longDate = startDate.getTime();
        query.add(AuditEntity.revisionProperty("timestamp").ge(longDate));

        List<PrintJobAuditModel> auditList = auditService.processPrintJobAuditQuery(query);
        Map<String, List<PrintJobColorCount>> data = groupPrintJobsByColorAndMonth(auditList);
        AuditPrintJobColorCountResponse response = new AuditPrintJobColorCountResponse();
        response.setData(data);
        response.setSuccess(true);
        response.setMessage("Successfully queried for print jobs grouped by colors and month");
        response.setHttpStatus(HttpStatus.OK);
        return response;
    }

    private Map<String, List<PrintJobColorCount>> groupPrintJobsByColorAndMonth(List<PrintJobAuditModel> auditList) {
        Map<String, List<PrintJobColorCount>> data = new HashMap<>();
        auditList.forEach(a -> {
            LocalDate localDate = a.getRevisionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String month = localDate.getMonth().toString() + " " + localDate.getYear();
            if(data.containsKey(month)) {
                List<PrintJobColorCount> counter = data.get(month);
                boolean isFound = false;
                for(PrintJobColorCount count : counter) {
                    if(count.getColor().equalsIgnoreCase(a.getColor())) {
                        int sum = count.getCount();
                        sum++;
                        count.setCount(sum);
                        isFound = true;
                        break;
                    }
                }
                if(!isFound) {
                    PrintJobColorCount count = new PrintJobColorCount();
                    count.setColor(a.getColor());
                    count.setCount(1);
                    counter.add(count);
                }
                data.put(month, counter);
            } else {
                List<PrintJobColorCount> counter = new ArrayList<>();
                PrintJobColorCount count = new PrintJobColorCount();
                count.setColor(a.getColor());
                count.setCount(1);
                counter.add(count);
                data.put(month, counter);
            }
        });
        return data;
    }
}
