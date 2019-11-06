package idealab.api.controller;

import idealab.api.dto.request.DropBoxFilePathRequest;
import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.PrintJobAuditModel;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.dto.response.PrintJobResponse;
import idealab.api.model.PrintJob;
import idealab.api.service.FileService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@RestController
@PersistenceContext
@RequestMapping("/api/audit")
public class AuditController {
    private final EntityManager entityManager;

    public AuditController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/printjobs")
    public ResponseEntity<?> allPrintJobsAuditTable() {
        GenericResponse response = new GenericResponse();

        response.setSuccess(true);
        response.setMessage("Successfully got all print job audit table endpoint");
        response.setHttpStatus(HttpStatus.ACCEPTED);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/printjobs/{print-id}")

    public ResponseEntity<?> printJobAuditTableGetById(@PathVariable("print-id") Integer printId) {
        PrintJobAuditResponse response = new PrintJobAuditResponse();

//        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
//        EntityManager entityManager = emfactory.createEntityManager();


        AuditReader reader = AuditReaderFactory.get( entityManager );

        AuditQuery query = reader.createQuery()
                .forRevisionsOfEntity(PrintJob.class, false, true);

        query.addOrder(AuditEntity.revisionNumber().desc());

//        List<PrintJob> printJobList = (List<PrintJob>)query.getResultList();
//        List<PrintJobAuditModel> auditList = new ArrayList<>();
//        for ( PrintJob printJob : printJobList){
//            PrintJobAuditModel auditModel = new PrintJobAuditModel();
//            auditModel.setId(printJob.getId());
//            auditModel.setFilePath(printJob.getFilePath());
//            //auditModel.setColorTypeId(printJob.getColorTypeId());
//            auditModel.setStatus(printJob.getStatus());
//            auditModel.setEmailHash(printJob.getEmailHash());
//            auditList.add(auditModel);
//        }
//
//        response.setSuccess(true);
//        response.setData(auditList);
//        response.setMessage("Success!");
//        response.setHttpStatus(HttpStatus.ACCEPTED);

        List<Object[]> printJobList = (List<Object[]>)query.getResultList();
        List<PrintJobAuditModel> auditList = new ArrayList<>();
        for ( Object[] printJobObject : printJobList){
            PrintJob printJob = (PrintJob) printJobObject[0];
            PrintJobAuditModel auditModel = new PrintJobAuditModel();
            auditModel.setId(printJob.getId());
            auditModel.setFilePath(printJob.getFilePath());
            //auditModel.setColorTypeId(printJob.getColorTypeId());
            auditModel.setStatus(printJob.getStatus());
            auditModel.setEmailHash(printJob.getEmailHash());

            DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) printJobObject[1];
            auditModel.setRevisionDate(defaultRevisionEntity.getRevisionDate());
            auditList.add(auditModel);
        }

        response.setSuccess(true);
        response.setData(auditList);
        response.setMessage("Success!");
        response.setHttpStatus(HttpStatus.ACCEPTED);



        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
