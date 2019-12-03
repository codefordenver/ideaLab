package idealab.api.repositories;

import idealab.api.model.CustomerInfo;
import idealab.api.model.PrintJob;
import idealab.api.model.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PrintJobRepo extends CrudRepository<PrintJob, Integer> {
    
	PrintJob findPrintJobById(Integer id);
    List<PrintJob> findByStatusIn(List<Status> statuses);
    List<PrintJob> findPrintJobByStatus(Status s);
    List<PrintJob> findAll();
    List<PrintJob> findByCustomerInfo(CustomerInfo customerInfoId);


    @Query(value = "SELECT p.* FROM print_job p " +
                    "where p.fk_customer_info_id = ?1 " +
                    "order by p.created_at DESC", nativeQuery = true)
    List<PrintJob> findPrintJobsByCustomerIdNewestFirst(Integer id);
}
