package idealab.api.repositories;

import idealab.api.model.Message;
import idealab.api.model.Status;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Integer> {
   Message findMessageByStatus(Status status);
}
