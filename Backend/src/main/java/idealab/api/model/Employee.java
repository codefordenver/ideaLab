package idealab.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(targetEntity=PrintStatus.class, mappedBy="employeeId")   
    private Set<PrintStatus> printStatus;

    @Column(name = "first_name", nullable = false)
    @Length(min = 1, max = 254)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Length(min = 1,  max = 254)
    private String lastName;

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //getter & setter
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
}