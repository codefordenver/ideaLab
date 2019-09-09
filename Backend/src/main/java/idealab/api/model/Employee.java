package idealab.api.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false)
    @Length (min = 1, max = 254)
    private String username;

    @Column(name = "password", nullable = false)
    @Length(min = 8)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

    @OneToMany(targetEntity=PrintJob.class, mappedBy="employeeId")
    private Set<PrintJob> printJobs;

    @Column(name = "first_name", nullable = false)
    @Length(min = 1, max = 254)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Length(min = 1,  max = 254)
    private String lastName;

    public Employee() {
    }

    public Employee(@Length(min = 1, max = 254) String username,
                    String password,
                    EmployeeRole role,
                    Set<PrintJob> printJobs,
                    @Length(min = 1, max = 254) String firstName,
                    @Length(min = 1, max = 254) String lastName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.printJobs = printJobs;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //getter & setter


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public Set<PrintJob> getPrintJobs() {
        return printJobs;
    }

    public void setPrintJobs(Set<PrintJob> printJobs) {
        this.printJobs = printJobs;
    }
}