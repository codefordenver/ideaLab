package idealab.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name ="customer_info")
public class CustomerInfo extends RecordTimestamp {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy="customerInfo", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<PrintJob> printJobs;

    @Column(name = "first_name", nullable = false)
    @Length(min = 1, max = 254)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Length(min = 1,  max = 254)
    private String lastName;

    @Column(name = "email", nullable = false)
    @Length(min = 1,  max = 254)
    private String email;

    public CustomerInfo() {}

    public CustomerInfo(Set<PrintJob> printJobs, String firstName, String lastName, String email) {
        this.printJobs = printJobs;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<PrintJob> getPrintJobs() {
        return printJobs;
    }

    public void setPrintJobs(Set<PrintJob> printJobs) {
        this.printJobs = printJobs;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("printJobs=[");
        if(printJobs != null && printJobs.size() > 0) {
            printJobs.forEach(p -> {
                sb.append(p.getId());
                sb.append(", ");
            });
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return "CustomerInfo{" +
                "id=" + id +
                ", " + sb.toString() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

