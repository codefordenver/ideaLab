package ideaLab.api.models;

import javax.persistence.*;

@Entity
@Table(name = "customer_info")
public class CustomerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String emailHashId;
    private String firstName;
    private String lastName;
    private String email;

    public CustomerInfo(Integer id, String emailHashId, String firstName, String lastName, String email) {
        this.id = id;
        this.emailHashId = emailHashId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailHashId() {
        return emailHashId;
    }

    public void setEmailHashId(String emailHashId) {
        this.emailHashId = emailHashId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

