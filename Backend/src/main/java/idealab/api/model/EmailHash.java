package idealab.api.model;

import javax.persistence.*;

@Entity
@Table(name = "email_hash")
public class EmailHash {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

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

