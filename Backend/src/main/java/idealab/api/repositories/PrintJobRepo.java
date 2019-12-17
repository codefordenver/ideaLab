package idealab.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import idealab.api.model.CustomerInfo;
import idealab.api.model.PrintJob;
import idealab.api.model.Status;

public interface PrintJobRepo extends CrudRepository<PrintJob, Integer> {
    
	  PrintJob findPrintJobById(Integer id);
    List<PrintJob> findByStatusIn(List<Status> statuses);
    List<PrintJob> findPrintJobByStatus(Status s);
    List<PrintJob> findAll();
    List<PrintJob> findByCustomerInfo(CustomerInfo customerInfoId);

    /**
     * This finds a list of all "active" print jobs. Active print jobs are any print job in 
     * the queue or any print job that does not have a status "waiting", "printing", or
     * "pending customer review"
     */
    @Query("FROM PrintJob p LEFT JOIN p.queueId q WHERE q.id is not null or (p.status != 'PENDING_REVIEW' "
        + "and p.status != 'PRINTING' and p.status != 'PENDING_CUSTOMER_RESPONSE')")
    List<PrintJob> findActive();
  
    @Query(value = "SELECT p.* FROM print_job p " +
                    "where p.fk_customer_info_id = ?1 " +
                    "order by p.created_at DESC", nativeQuery = true)
    List<PrintJob> findPrintJobsByCustomerIdNewestFirst(Integer id);
}
