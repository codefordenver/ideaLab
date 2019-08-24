package idealab.api.repositories;

import org.springframework.data.repository.CrudRepository;

import idealab.api.model.EmailHash;

public interface EmailHashRepo extends CrudRepository<EmailHash, Integer> {
}
