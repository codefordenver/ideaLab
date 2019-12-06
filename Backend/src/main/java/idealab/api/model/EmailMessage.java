package idealab.api.model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "email_message")
public class EmailMessage extends RecordTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status",  nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "email_message")
    private String message;

    public EmailMessage() {
    }

    public EmailMessage(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

