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
@Table(name = "email_hash")
public class EmailHash {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToMany(targetEntity=CustomerInfo.class, mappedBy="emailHashId")   
    private Set<CustomerInfo> customerInfo;

    @OneToMany(targetEntity=PrintModel.class, mappedBy="emailHashId")   
    private Set<PrintModel> printModel;

    @Column(name = "email_hash", nullable = false)
    private String emailHash;

    public EmailHash(Integer id, String emailHash) {
        this.id = id;
        this.emailHash = emailHash;
    }

    //getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

}

