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
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static idealab.api.exception.ErrorType.COLOR_CANT_FIND_BY_TYPE;
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

        List<PrintJobAuditModel> auditList = processPrintJobAuditQuery(query);
        PrintJobAuditResponse response = new PrintJobAuditResponse(auditList);
        return response;
    }

    public PrintJobAuditResponse printJobAuditById(Integer printJobId){
        AuditReader reader = AuditReaderFactory.get( entityManager );
        AuditQuery query = reader.createQuery()
                .forRevisionsOfEntity(PrintJob.class, false, true);
        query.addOrder(AuditEntity.revisionNumber().desc());

        //add method allows filtering, this is the only difference between getting all print jobs
        query.add(AuditEntity.property("id").eq(printJobId));

        List<PrintJobAuditModel> auditList = processPrintJobAuditQuery(query);
        PrintJobAuditResponse response = new PrintJobAuditResponse(auditList);
        return response;
    }

    private List<PrintJobAuditModel> processPrintJobAuditQuery (AuditQuery query){
        List<Object[]> printJobList = (List<Object[]>)query.getResultList();
        List<PrintJobAuditModel> auditList = new ArrayList<>();
        for ( Object[] printJobObject : printJobList){
            //Index [0] - Returns items that are directly on the PrintJob model
            PrintJob printJob = (PrintJob) printJobObject[0];
            PrintJobAuditModel auditModel = new PrintJobAuditModel();
            auditModel.setId(printJob.getId());
            auditModel.setFilePath(printJob.getFilePath());
            auditModel.setStatus(printJob.getStatus());
            auditModel.setEmailHash(printJob.getEmailHash());

            ColorType colorTypeId = printJob.getColorTypeId();
            auditModel.setColor(colorTypeId.getColor());

            //Index [1] - Revision date comes from the Revision Entity Table
            DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) printJobObject[1];
            auditModel.setRevisionDate(defaultRevisionEntity.getRevisionDate());

            //Index [2] - Returns the enum of the revision type
            //ADD = Added (persisted) to the database
            //DEL = Deleted (removed) from the database
            //MOD = Modified (1 or more of its fields) in the database
            RevisionType revisionType = (RevisionType) printJobObject[2];
            auditModel.setRevisionType(revisionType.name());
            auditList.add(auditModel);
        }

        return auditList;
    }
}
