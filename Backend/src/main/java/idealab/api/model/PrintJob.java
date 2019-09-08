package idealab.api.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * This class holds the model that represents the print job table. It is related to email hash, color type, queue, and employee ID. Additionally,
 * it has a spot for comments, dropbox link, and a way to state when it was created + last updated.
 */
@Entity
@Table(name = "print_job")
public class PrintJob {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name="fk_email_hash_id", referencedColumnName = "id", nullable = false)
    private EmailHash emailHashId;

    @ManyToOne()
    @JoinColumn(name="fk_color_type_id", referencedColumnName = "id", nullable = false)
    private ColorType colorTypeId;

    @ManyToOne()
    @JoinColumn(name="fk_employee_id", referencedColumnName = "id", nullable = false)
    private Employee employeeId;

    @Column(name = "status",  nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(targetEntity=Queue.class, mappedBy="printJobId")
    private Queue queueId;

    @Column(name = "comments")
    private String comments;

    @Column(name = "dropbox_link", nullable = false)
    @Length(min = 1, max = 254)
    private String dropboxLink;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_at",  nullable = false)
    private LocalDateTime createdAt;

    public PrintJob() {
    }

    public PrintJob(EmailHash emailHashId, ColorType colorTypeId, Employee employeeId, Status status,
                     Queue queueId, String comments,
                    @Length(min = 1, max = 254) String dropboxLink, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.emailHashId = emailHashId;
        this.colorTypeId = colorTypeId;
        this.employeeId = employeeId;
        this.status = status;
        this.queueId = queueId;
        this.comments = comments;
        this.dropboxLink = dropboxLink;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmailHash getEmailHashId() {
        return emailHashId;
    }

    public void setEmailHashId(EmailHash emailHashId) {
        this.emailHashId = emailHashId;
    }

    public ColorType getColorTypeId() {
        return this.colorTypeId;
    }

    public void setColorTypeId(ColorType colorTypeId) {
        this.colorTypeId = colorTypeId;
    }

    public Employee getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Queue getQueueId() {
        return this.queueId;
    }

    public void setQueueId(Queue queueId) {
        this.queueId = queueId;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDropboxLink() {
        return this.dropboxLink;
    }

    public void setDropboxLink(String dropboxLink) {
        this.dropboxLink = dropboxLink;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintJob printJob = (PrintJob) o;
        return Objects.equals(id, printJob.id) &&
                Objects.equals(emailHashId, printJob.emailHashId) &&
                Objects.equals(colorTypeId, printJob.colorTypeId) &&
                Objects.equals(employeeId, printJob.employeeId) &&
                status == printJob.status &&
                Objects.equals(queueId, printJob.queueId) &&
                Objects.equals(comments, printJob.comments) &&
                Objects.equals(dropboxLink, printJob.dropboxLink) &&
                Objects.equals(updatedAt, printJob.updatedAt) &&
                Objects.equals(createdAt, printJob.createdAt);
    }

    @Override
    public String toString() {
        return "PrintJob{" +
                "id=" + id +
                ", emailHashId=" + emailHashId +
                ", colorTypeId=" + colorTypeId +
                ", employeeId=" + employeeId +
                ", status=" + status +
                ", queueId=" + queueId +
                ", comments='" + comments + '\'' +
                ", dropboxLink='" + dropboxLink + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
