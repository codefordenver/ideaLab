package ideaLab.api.models;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "print_status")
public class PrintStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer queue;
    private Integer printModelId;
    private LocalDateTime statusDate;
    private Status status;
    private Integer employeeId;
    private String employeeNotes;

    public PrintStatus(Integer id, Integer queue, Integer printModelId, LocalDateTime statusDate, Status status, Integer employeeId, String employeeNotes) {
        this.id = id;
        this.queue = queue;
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

    public Integer getQueue() {
        return queue;
    }

    public void setQueue(Integer queue) {
        this.queue = queue;
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
