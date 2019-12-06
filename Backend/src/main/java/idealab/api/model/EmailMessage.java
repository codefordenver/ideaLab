package idealab.api.model;

import javax.persistence.*;


@Entity
@Table(name = "email_message")
public class EmailMessage extends RecordTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "email_message")
    private String emailMessage;

    public EmailMessage() {
    }

    public EmailMessage(Status status, String emailMessage) {
        this.status = status;
        this.emailMessage = emailMessage;
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

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }
}

