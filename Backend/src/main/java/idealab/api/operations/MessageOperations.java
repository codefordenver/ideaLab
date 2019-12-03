package idealab.api.operations;

import idealab.api.dto.response.GenericResponse;
import idealab.api.dto.response.MessageResponse;
import idealab.api.dto.response.PrintJobAuditModel;
import idealab.api.dto.response.PrintJobAuditResponse;
import idealab.api.service.AuditService;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageOperations {
    public MessageOperations() {
    }

    public MessageResponse getMessageByStatus(){

    }

    public GenericResponse sendMessage(){

    }

    public GenericResponse updateMessage(){

    }
}
