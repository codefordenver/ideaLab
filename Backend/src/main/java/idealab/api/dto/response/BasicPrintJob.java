package idealab.api.dto.response;

import idealab.api.model.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BasicPrintJob {
    private Integer id;
    private CustomerInfo customerInfo;
    private ColorType colorType;
    private Status status;
    private Queue queueId;
    private String comments;
    private String fileSharableLink;
    private String filePath;
    private String emailHash;

    public BasicEmployee employee;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BasicPrintJob(){};

    public BasicPrintJob(PrintJob printJob){
        this.id = printJob.getId();
        this.customerInfo = printJob.getCustomerInfo();
        this.colorType = printJob.getColorType();
        if(printJob.getEmployee() != null){
            this.employee = new BasicEmployee(printJob.getEmployee().getUsername(), printJob.getEmployee().getRole(), printJob.getEmployee().getFirstName(), printJob.getEmployee().getLastName());
        }
        this.status = printJob.getStatus();
        this.queueId = printJob.getQueueId();
        this.comments = printJob.getComments();
        this.fileSharableLink = printJob.getFileSharableLink();
        this.filePath = printJob.getFilePath();
        this.emailHash = printJob.getEmailHash();
        this.createdAt = printJob.getCreatedAt();
        this.updatedAt = printJob.getUpdatedAt();
    }

    public List<BasicPrintJob> ConvertPrintJobs(List<PrintJob> printJobs) {
        return printJobs.stream()
                        .map(printJob -> new BasicPrintJob(printJob)).collect(Collectors.toList());
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

    public ColorType getColorType() {
        return colorType;
    }

    public void setColorType(ColorType colorType) {
        this.colorType = colorType;
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

    public BasicEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(BasicEmployee employee) {
        this.employee = employee;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicPrintJob basicPrintJob = (BasicPrintJob) o;
        return Objects.equals(id, basicPrintJob.id) &&
                Objects.equals(customerInfo, basicPrintJob.customerInfo) &&
                Objects.equals(colorType, basicPrintJob.colorType) &&
                Objects.equals(employee, basicPrintJob.employee) &&
                status == basicPrintJob.status &&
                Objects.equals(queueId, basicPrintJob.queueId) &&
                Objects.equals(comments, basicPrintJob.comments) &&
                Objects.equals(fileSharableLink, basicPrintJob.fileSharableLink) &&
                Objects.equals(filePath, basicPrintJob.filePath) &&
                Objects.equals(emailHash, basicPrintJob.emailHash);
    }
}
