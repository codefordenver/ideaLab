package idealab.api.repositories;

import org.springframework.data.repository.CrudRepository;

import idealab.api.model.PrintStatus;

public interface PrintStatusRepo extends CrudRepository<PrintStatus, Integer> {
    PrintStatus getPrintStatusById(Integer id);
    PrintStatus save(PrintStatus printStatus);
}
