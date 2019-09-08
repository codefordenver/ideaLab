package idealab.api.model;

import javax.persistence.*;
import java.util.Set;



@Entity
@Table(name = "email_hash")
public class EmailHash {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(targetEntity=CustomerInfo.class, mappedBy="emailHashId")   
    private Set<CustomerInfo> customerInfo;

    @OneToMany(targetEntity=PrintJob.class, mappedBy="emailHashId")
    private Set<PrintJob> printJobs;

    @Column(name = "email_hash", nullable = false)
    private String emailHash;

    public EmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

    //getters and setters
    public String getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

}

