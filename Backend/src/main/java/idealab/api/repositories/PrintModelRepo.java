package idealab.api.repositories;

import org.springframework.data.repository.CrudRepository;

import idealab.api.model.PrintModel;

public interface PrintModelRepo extends CrudRepository<PrintModel, Integer> {
   PrintModel getPrintModelById(Integer id);
}
