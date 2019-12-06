package idealab.api.repositories;

import idealab.api.model.EmailMessage;
import idealab.api.model.Status;
import org.springframework.data.repository.CrudRepository;

public interface EmailMessageRepo extends CrudRepository<EmailMessage, Integer> {
   EmailMessage findEmailMessageByStatus(Status status);
}
