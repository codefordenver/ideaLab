package idealab.api.repositories;

import org.springframework.data.repository.CrudRepository;

import idealab.api.model.CustomerInfo;
import idealab.api.model.EmailHash;

public interface CustomerInfoRepo extends CrudRepository<CustomerInfo, Integer> {
   CustomerInfo findByEmailHashId(EmailHash emailHash);
}
