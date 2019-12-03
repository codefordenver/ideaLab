package idealab.api.dto.response;

import idealab.api.model.ColorType;
import idealab.api.model.PrintJob;
import idealab.api.model.Status;
import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PrintJobAuditModel {
    private Integer id;
    private String filePath;
    private String color;
    private Status status;
    private String emailHash;
    private Date revisionDate;
    private String revisionType;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

    public String getRevisionType() {
        return revisionType;
    }

    public void setRevisionType(String revisionType) {
        this.revisionType = revisionType;
    }



    public PrintJobAuditModel(){}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintJobAuditModel printJobAuditModel = (PrintJobAuditModel) o;
        return Objects.equals(id, printJobAuditModel.id) &&
                Objects.equals(revisionDate, printJobAuditModel.revisionDate) &&
                Objects.equals(revisionType, printJobAuditModel.revisionType) &&
                Objects.equals(status, printJobAuditModel.status) &&
                Objects.equals(filePath, printJobAuditModel.filePath) &&
                Objects.equals(emailHash, printJobAuditModel.emailHash);
    }

    @Override
    public String toString() {
        return "PrintJob{" +
                "id=" + id +
                ", customerInfo=" + revisionDate +
                ", colorTypeId=" + revisionType +
                ", status=" + status +
                ", filePath='" + filePath + '\'' +
                ", emailHash='" + emailHash + '\'' +
                '}';
    }

}