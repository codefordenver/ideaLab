package idealab.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "status_type")
public class StatusType {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToMany(targetEntity=StatusType.class, mappedBy="statusTypeId")   
    private Set<StatusType> statusType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "send_email", nullable = false)
    private Boolean sendEmail;

    public StatusType(Integer id, String status, Boolean sendEmail) {
        this.id = id;
        this.status = status;
        this.sendEmail = sendEmail;
    }

    //getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
}

