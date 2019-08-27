package idealab.api.repositories;

import idealab.api.model.PrintJob;
import org.springframework.data.repository.CrudRepository;

public interface PrintJobRepo extends CrudRepository<PrintJob, Integer> {
    PrintJob findPrintJobById(Integer id);

}
