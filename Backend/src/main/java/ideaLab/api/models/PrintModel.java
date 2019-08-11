package ideaLab.api.models;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "print_model")
public class PrintModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String emailHashId;
    private ColorChoice color;
    private String comments;
    private String dropboxLink;
    private LocalDateTime updatedAt;
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