package idealab.api.service;

import idealab.api.dto.response.PrintJobAuditModel;
import idealab.api.model.ColorType;
import idealab.api.model.PrintJob;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@PersistenceContext
public class AuditUtil {
    private final EntityManager entityManager;

    public AuditUtil(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AuditQuery getPrintJobAuditQuery(){
        AuditReader reader = AuditReaderFactory.get( entityManager );
        AuditQuery query = reader.createQuery()
                .forRevisionsOfEntity(PrintJob.class, false, true);

        return query;
    }

    public List<PrintJobAuditModel> processPrintJobAuditQuery(AuditQuery query){
        List<Object[]> printJobList = query.getResultList();
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
