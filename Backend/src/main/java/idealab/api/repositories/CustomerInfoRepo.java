package idealab.api.repositories;

import org.springframework.data.repository.CrudRepository;

import idealab.api.model.CustomerInfo;

public interface CustomerInfoRepo extends CrudRepository<CustomerInfo, Integer> {
   CustomerInfo findByEmailHash(String emailHash);
}
