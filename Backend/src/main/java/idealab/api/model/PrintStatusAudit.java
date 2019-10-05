package idealab.api.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "print_status_audit")
public class PrintStatusAudit extends RecordTimestamp {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fk_employee_id")
    private Integer employee;

    @Column(name = "status_before")
    private String statusBefore;

    @Column(name = "status_after")
    private String statusAfter;

    @Column(name = "fk_print_model_id", nullable = false)
    private Integer printModelId;

    public PrintStatusAudit() {
    }

    public PrintStatusAudit(Integer employee, String statusBefore, String statusAfter) {
        this.employee = employee;
        this.statusBefore = statusBefore;
        this.statusAfter = statusAfter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
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
                ", employee=" + employee +
                ", statusBefore='" + statusBefore + '\'' +
                ", statusAfter='" + statusAfter + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintStatusAudit that = (PrintStatusAudit) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(employee, that.employee) &&
                Objects.equals(statusBefore, that.statusBefore) &&
                Objects.equals(statusAfter, that.statusAfter);
    }

}
