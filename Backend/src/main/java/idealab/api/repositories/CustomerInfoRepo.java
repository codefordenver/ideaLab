package idealab.api.repositories;

import idealab.api.model.CustomerInfo;
import org.springframework.data.repository.CrudRepository;

public interface CustomerInfoRepo extends CrudRepository<CustomerInfo, Integer> {
   CustomerInfo findByEmail(String email);
}
