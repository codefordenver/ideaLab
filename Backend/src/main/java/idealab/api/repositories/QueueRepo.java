package idealab.api.repositories;

import org.springframework.data.repository.CrudRepository;

import idealab.api.model.PrintJob;
import idealab.api.model.Queue;

public interface QueueRepo extends CrudRepository<Queue, Integer> {
    Queue findByPrintJobId(PrintJob printJobId);

}
