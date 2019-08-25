package idealab.api.repositories;

import idealab.api.model.PrintStatus;
import org.springframework.data.repository.CrudRepository;

public interface PrintStatusRepo extends CrudRepository<PrintStatus, Integer> {
    PrintStatus getPrintStatusById(Integer id);
    PrintStatus save(PrintStatus printStatus);
    void delete(PrintStatus printStatus);
}
