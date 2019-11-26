package idealab.api.operations;

import idealab.api.dto.response.PrintJobAuditModel;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.service.AuditService;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
