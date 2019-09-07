package idealab.api.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "print_status_audit")
public class PrintStatusAudit {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fk_employee_id")
    private Integer employeeId;

    @Column(name = "status_before")
    private String statusBefore;

    @Column(name = "status_after")
    private String statusAfter;

    @Column(name = "created_at",  nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "fk_print_model_id", nullable = false)
    private Integer printModelId;

    public PrintStatusAudit() {
    }

    public PrintStatusAudit(Integer employeeId, String statusBefore, String statusAfter, LocalDateTime createdAt) {
        this.employeeId = employeeId;
        this.statusBefore = statusBefore;
        this.statusAfter = statusAfter;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatusBefore() {
        return statusBefore;
    }

    public void setStatusBefore(String statusBefore) {
        this.statusBefore = statusBefore;
    }

    public String getStatusAfter() {
        return statusAfter;
    }

    public void setStatusAfter(String statusAfter) {
        this.statusAfter = statusAfter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPrintModelId(Integer id) {
        this.printModelId = id;
    }

    public Integer getPrintModelId() {
        return this.printModelId;
    }

    @Override
    public String toString() {
        return "PrintStatusAudit{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", statusBefore='" + statusBefore + '\'' +
                ", statusAfter='" + statusAfter + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintStatusAudit that = (PrintStatusAudit) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(statusBefore, that.statusBefore) &&
                Objects.equals(statusAfter, that.statusAfter) &&
                Objects.equals(createdAt, that.createdAt);
    }

}
