package com.notiflowcate.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Dayel Ostraco
 * 12/21/15
 */
@Entity
@Table(name = "Topic")
public class Topic implements Serializable {

    private static final long serialVersionUID = 886614619271280020L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topicId", nullable = false)
    private Long topicId;

    @Column(name = "applicationId", nullable = false, length = 36)
    private Long applicationId;

    @Column(name = "topicName", nullable = false, length = 64)
    @Pattern(regexp = "[a-zA-Z0-9-_.~%]+")
    private String topicName;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "deleted")
    private LocalDateTime deleted;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @PrePersist
    protected void onCreate() {
        created = modified = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDateTime deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", applicationId=" + applicationId +
                ", topicName='" + topicName + '\'' +
                ", active=" + active +
                ", deleted=" + deleted +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;

        Topic topic = (Topic) o;

        if (topicId != null ? !topicId.equals(topic.topicId) : topic.topicId != null) return false;
        if (applicationId != null ? !applicationId.equals(topic.applicationId) : topic.applicationId != null)
            return false;
        if (topicName != null ? !topicName.equals(topic.topicName) : topic.topicName != null) return false;
        if (active != null ? !active.equals(topic.active) : topic.active != null) return false;
        if (deleted != null ? !deleted.equals(topic.deleted) : topic.deleted != null) return false;
        if (created != null ? !created.equals(topic.created) : topic.created != null) return false;
        return modified != null ? modified.equals(topic.modified) : topic.modified == null;

    }

    @Override
    public int hashCode() {
        int result = topicId != null ? topicId.hashCode() : 0;
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (topicName != null ? topicName.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
