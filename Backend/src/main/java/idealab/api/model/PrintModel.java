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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "fk_email_hash_id", referencedColumnName = "id", nullable = false)
    private EmailHash emailHashId;

    @ManyToOne()
    @JoinColumn(name = "fk_color_type_id", referencedColumnName = "id", nullable = false)
    private ColorType colorTypeId;

    @OneToMany(targetEntity = PrintStatus.class, mappedBy = "printModelId")
    private Set<PrintStatus> printModel;

    @OneToOne(targetEntity = Queue.class, mappedBy = "printModelId")
    private Queue queueId;

    @Column(name = "comments")
    private String comments;

    @Column(name = "dropbox_sharable_link")
    @Length(min = 1, max = 254)
    private String dropboxSharableLink;

    @Column(name = "dropbox_path")
    @Length(min = 1, max = 254)
    private String dropboxPath;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public PrintModel() {
    }

    public PrintModel(EmailHash emailHashId, ColorType color, String comments, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.emailHashId = emailHashId;
        this.comments = comments;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.colorTypeId = color;
    }

    //getters and setters
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}