package idealab.api.repositories;

import idealab.api.model.PrintJob;
import idealab.api.model.Status;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrintJobRepo extends CrudRepository<PrintJob, Integer> {
    
	PrintJob findPrintJobById(Integer id);
    List<PrintJob> findByStatusIn(List<Status> statuses);
    List<PrintJob> findPrintJobByStatus(Status s);
    List<PrintJob> findAll();
    
}
