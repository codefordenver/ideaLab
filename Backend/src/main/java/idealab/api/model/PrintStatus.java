package idealab.api.model;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "print_status")
public class PrintStatus {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "print_model_id", nullable = false)
    private Integer printModelId;

    @Column(name = "status_date",  nullable = false)
    private LocalDateTime statusDate;

    @Column(name = "status",  nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "employee_id",  nullable = false)
    private Integer employeeId;

    @Column(name = "employee_notes",  nullable = false)
    private String employeeNotes;

    public PrintStatus(Integer id, Integer queue, Integer printModelId, LocalDateTime statusDate, Status status, Integer employeeId, String employeeNotes) {
        this.id = id;
        this.printModelId = printModelId;
        this.statusDate = statusDate;
        this.status = status;
        this.employeeId = employeeId;
        this.employeeNotes = employeeNotes;
    }
    //getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrintModelId() {
        return printModelId;
    }

    public void setPrintModelId(Integer printModelId) {
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

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeNotes() {
        return employeeNotes;
    }

    public void setEmployeeNotes(String employeeNotes) {
        this.employeeNotes = employeeNotes;
    }
}
