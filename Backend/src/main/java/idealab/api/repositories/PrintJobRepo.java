package idealab.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import idealab.api.model.PrintJob;
import idealab.api.model.Status;

public interface PrintJobRepo extends CrudRepository<PrintJob, Integer> {
    PrintJob findPrintJobById(Integer id);
    List<PrintJob> findByStatusIn(List<Status> statuses);
    PrintJob findPrintJobByStatus(Status s);

    @Query(value = "from print_job t where created_at >= current_date and fk_email_hash_id = emailHashId")
    List<PrintJob> findByEmailHashAndCreatedToday(@Param("emailHashId") Integer emailHashId);

    List<PrintJob> findAll();
}
