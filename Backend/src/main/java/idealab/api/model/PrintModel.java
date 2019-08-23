package idealab.api.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "print_model")
public class PrintModel {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name="fk_email_hash_id", referencedColumnName = "id", nullable = false)   
    private EmailHash emailHashId;

    @ManyToOne()
    @JoinColumn(name="fk_color_type_id", referencedColumnName = "id", nullable = false)   
    private ColorType colorTypeId;

    @OneToMany(targetEntity=PrintStatus.class, mappedBy="printModelId")   
    private Set<PrintStatus> printModel;

    @OneToOne(targetEntity=Queue.class, mappedBy="printModelId")
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

    public PrintModel(EmailHash emailHashId, ColorType color, String comments, String dropboxLink, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.emailHashId = emailHashId;
        this.comments = comments;
        this.dropboxLink = dropboxLink;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.colorTypeId = color;
    }

    //getters and setters
    public EmailHash getEmailHashId() {
        return emailHashId;
    }

    public void setEmailHashId(EmailHash emailHashId) {
        this.emailHashId = emailHashId;
    }

    public ColorType getColorType() {
        return colorTypeId;
    }

    public void setColorType(ColorType color) {
        this.colorTypeId = color;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDropboxLink() {
        return dropboxLink;
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
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}