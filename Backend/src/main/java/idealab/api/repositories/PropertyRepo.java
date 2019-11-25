package idealab.api.repositories;

import idealab.api.model.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepo extends CrudRepository<Property, Integer> {

    Property findByName(String name);

}
