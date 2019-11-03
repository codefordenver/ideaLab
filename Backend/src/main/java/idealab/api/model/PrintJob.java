package idealab.api.model;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Objects;

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

    @NotAudited
    @OneToOne(targetEntity=Queue.class, mappedBy="printJobId")
    private Queue queueId;

    @Column(name = "comments")
    private String comments;

    @Column(name = "dropbox_sharable_link")
    @Length(min = 1, max = 254)
    private String fileSharableLink;

    @Column(name = "dropbox_path")
    @Length(min = 1, max = 254)
    private String filePath;

    @Column(name = "email_hash")
    @Length(min = 1, max = 254)
    private String emailHash;

    public PrintJob() {}

    public PrintJob(CustomerInfo customerInfo, ColorType colorTypeId, Employee employeeId,
    		Status status, String comments, String emailHash) {
        this.customerInfo = customerInfo;
        this.colorTypeId = colorTypeId;
        this.employeeId = employeeId;
        this.status = status;
        this.comments = comments;
        //Make the email hash directly on the record so it is added to the audit table
        this.emailHash =  emailHash;
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

    public String getFileSharableLink() {
        return fileSharableLink;
    }

    public void setFileSharableLink(String fileSharableLink) {
        this.fileSharableLink = fileSharableLink;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
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
                Objects.equals(fileSharableLink, printJob.fileSharableLink) &&
                Objects.equals(filePath, printJob.filePath) &&
                Objects.equals(emailHash, printJob.emailHash);
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
                ", dropboxSharableLink='" + fileSharableLink + '\'' +
                ", dropboxPath='" + filePath + '\'' +
                ", emailHash='" + emailHash + '\'' +
                '}';
    }
}
