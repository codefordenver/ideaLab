package idealab.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "print_status")
public class PrintStatus {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name="fk_print_model_id", referencedColumnName = "id", nullable = false)
    private PrintModel printModelId;

    @Column(name = "status_date",  nullable = false)
    private LocalDateTime statusDate;

    @Column(name = "status",  nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne()
    @JoinColumn(name="fk_employee_id", referencedColumnName = "id", nullable = false)
    private Employee employeeId;


    @Column(name = "employee_notes")
    private String employeeNotes;

    public PrintStatus(Integer queue, PrintModel printModelId, LocalDateTime statusDate, Status status, Employee employeeId, String employeeNotes) {   
        this.printModelId = printModelId;
        this.statusDate = statusDate;
        this.status = status;
        this.employeeId = employeeId;
        this.employeeNotes = employeeNotes;
    }
    
    //getters and setters
    public PrintModel getPrintModelId() {
        return printModelId;
    }

    public void setPrintModelId(PrintModel printModelId) {
        this.printModelId = printModelId;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeNotes() {
        return employeeNotes;
    }

    public void setEmployeeNotes(String employeeNotes) {
        this.employeeNotes = employeeNotes;
    }
}
