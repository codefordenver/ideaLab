package idealab.api.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import idealab.api.model.PrintJob;
import idealab.api.model.Queue;

public interface QueueRepo extends CrudRepository<Queue, Integer> {
    Queue findByPrintJobId(PrintJob printJobId);

    @Query(value = "SELECT max(rank) FROM queue")
    long getMaximumRank();

}
