package idealab.api.model;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Objects;

import static org.hibernate.envers.RelationTargetAuditMode.AUDITED;
import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

/**
 * This class holds the model that represents the print job table. It is related to email hash, color type, queue, and employee ID. Additionally,
 * it has a spot for comments, dropbox link, and a way to state when it was created + last updated.
 */
@Entity
@Audited
@Table(name = "print_job")
public class PrintJob extends RecordTimestamp {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @Audited(targetAuditMode = NOT_AUDITED)
    @JoinColumn(name="fk_customer_info_id", referencedColumnName = "id", nullable = false)
    private CustomerInfo customerInfo;

    @ManyToOne()
    @Audited(targetAuditMode = NOT_AUDITED)
    @JoinColumn(name="fk_color_type_id", referencedColumnName = "id", nullable = false)
    private ColorType colorTypeId;

    @ManyToOne()
    @Audited(targetAuditMode = NOT_AUDITED)
    @JoinColumn(name="fk_employee_id", referencedColumnName = "id", nullable = false)
    private Employee employeeId;

    @Column(name = "status",  nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(targetEntity=Queue.class, mappedBy="printJobId")
    private Queue queueId;

    @Column(name = "comments")
    private String comments;

    @Column(name = "dropbox_sharable_link")
    @Length(min = 1, max = 254)
    private String dropboxSharableLink;

    @Column(name = "dropbox_path")
    @Length(min = 1, max = 254)
    private String dropboxPath;

    @Column(name = "email_hash")
    @Length(min = 1, max = 254)
    private String emailHash;

    public PrintJob() {}

    public PrintJob(CustomerInfo customerInfo, ColorType colorTypeId, Employee employeeId,
    		Status status, String comments) {
        this.customerInfo = customerInfo;
        this.colorTypeId = colorTypeId;
        this.employeeId = employeeId;
        this.status = status;
        this.comments = comments;
        //Make the email hash directly on the record so it is added to the audit table
        this.emailHash = "make this a hash!" + customerInfo.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public ColorType getColorTypeId() {
        return colorTypeId;
    }

    public void setColorTypeId(ColorType colorTypeId) {
        this.colorTypeId = colorTypeId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Queue getQueueId() {
        return queueId;
    }

    public void setQueueId(Queue queueId) {
        this.queueId = queueId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDropboxSharableLink() {
        return dropboxSharableLink;
    }

    public void setDropboxSharableLink(String dropboxSharableLink) {
        this.dropboxSharableLink = dropboxSharableLink;
    }

    public String getDropboxPath() {
        return dropboxPath;
    }

    public void setDropboxPath(String dropboxPath) {
        this.dropboxPath = dropboxPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintJob printJob = (PrintJob) o;
        return Objects.equals(id, printJob.id) &&
                Objects.equals(customerInfo, printJob.customerInfo) &&
                Objects.equals(colorTypeId, printJob.colorTypeId) &&
                Objects.equals(employeeId, printJob.employeeId) &&
                status == printJob.status &&
                Objects.equals(queueId, printJob.queueId) &&
                Objects.equals(comments, printJob.comments) &&
                Objects.equals(dropboxSharableLink, printJob.dropboxSharableLink) &&
                Objects.equals(dropboxPath, printJob.dropboxPath);
    }

    @Override
    public String toString() {
        String custId = customerInfo.getId() != null ? String.valueOf(customerInfo.getId()) : "";
        return "PrintJob{" +
                "id=" + id +
                ", customerInfo=" + custId +
                ", colorTypeId=" + colorTypeId +
                ", employeeId=" + employeeId +
                ", status=" + status +
                ", queueId=" + queueId +
                ", comments='" + comments + '\'' +
                ", dropboxSharableLink='" + dropboxSharableLink + '\'' +
                ", dropboxPath='" + dropboxPath + '\'' +
                '}';
    }
}
