package idealab.api.repositories;

import java.util.List;

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
    
}
