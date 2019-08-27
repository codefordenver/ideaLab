package idealab.api.repositories;

import org.springframework.data.repository.CrudRepository;

import idealab.api.model.CustomerInfo;

public interface CustomerInfoRepo extends CrudRepository<CustomerInfo, Integer> {
    CustomerInfo getCustomerInfoById(Integer id);
    CustomerInfo save(CustomerInfo customerInfo);
}