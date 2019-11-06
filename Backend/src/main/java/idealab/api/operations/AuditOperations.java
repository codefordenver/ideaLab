package idealab.api.operations;

import idealab.api.dto.response.PrintJobAuditModel;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.exception.IdeaLabApiException;
import idealab.api.model.ColorType;
import idealab.api.model.PrintJob;
import idealab.api.repositories.ColorTypeRepo;
import idealab.api.repositories.PrintJobRepo;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static idealab.api.exception.ErrorType.PRINT_JOB_CANT_FIND_BY_ID;

@Service
@PersistenceContext
public class AuditOperations {
    private final PrintJobRepo printJobRepo;
    private final ColorTypeRepo colorTypeRepo;
    private final EntityManager entityManager;

    public AuditOperations(PrintJobRepo printJobRepo,
                              ColorTypeRepo colorTypeRepo, EntityManager entityManager) {

        this.printJobRepo = printJobRepo;
        this.colorTypeRepo = colorTypeRepo;
        this.entityManager = entityManager;
    }

    public PrintJobAuditResponse allPrintJobsAudit(){
        AuditReader reader = AuditReaderFactory.get( entityManager );

        AuditQuery query = reader.createQuery()
                .forRevisionsOfEntity(PrintJob.class, false, true);

        query.addOrder(AuditEntity.revisionNumber().desc());

        List<Object[]> printJobList = (List<Object[]>)query.getResultList();
        List<PrintJobAuditModel> auditList = new ArrayList<>();
        for ( Object[] printJobObject : printJobList){
            //Returns items that are directly on the PrintJob model
            PrintJob printJob = (PrintJob) printJobObject[0];
            PrintJobAuditModel auditModel = new PrintJobAuditModel();
            auditModel.setId(printJob.getId());
            auditModel.setFilePath(printJob.getFilePath());
            auditModel.setStatus(printJob.getStatus());
            auditModel.setEmailHash(printJob.getEmailHash());

            //ColorTypeId can't be serialzed directly
            //ColorType colorTypeId = printJob.getColorTypeId();

            //Revision date comes from the envers REV table
            DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) printJobObject[1];
            auditModel.setRevisionDate(defaultRevisionEntity.getRevisionDate());

            auditList.add(auditModel);
        }

        PrintJobAuditResponse response = new PrintJobAuditResponse(auditList);
        return response;
    }

    public PrintJobAuditResponse printJobAuditById(Integer printJobId){
        PrintJobAuditResponse response = new PrintJobAuditResponse("Still working on this :)");
        return response;
    }
}
