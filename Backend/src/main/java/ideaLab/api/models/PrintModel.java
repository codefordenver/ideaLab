package ideaLab.api.models;

import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "print_model")
public class PrintModel {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "email_hash_id", nullable = false)
    private String emailHashId;

    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorChoice color;

    @Column(name = "comments")
    private String comments;

    @Column(name = "dropbox_link", nullable = false)
    @Length(min = 1, max = 254)
    private String dropboxLink;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_at",  nullable = false)
    private LocalDateTime createdAt;

    public PrintModel(Integer id, String emailHashId, ColorChoice color, String comments, String dropboxLink, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.emailHashId = emailHashId;
        this.color = color;
        this.comments = comments;
        this.dropboxLink = dropboxLink;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    //getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailHashId() {
        return emailHashId;
    }

    public void setEmailHashId(String emailHashId) {
        this.emailHashId = emailHashId;
    }

    public ColorChoice getColor() {
        return color;
    }

    public void setColor(ColorChoice color) {
        this.color = color;
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