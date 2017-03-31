package com.notiflowcate.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Dayel Ostraco
 * 5/25/16
 */
@Entity
@DiscriminatorValue(value = "TOPIC")
public class TopicNotification extends Notification {

    private static final long serialVersionUID = 479447259438697076L;

    @Column(name = "topicId", nullable = false)
    private Long topicId;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return "TopicNotification{" +
                "topicId=" + topicId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicNotification)) return false;
        if (!super.equals(o)) return false;

        TopicNotification that = (TopicNotification) o;

        return topicId != null ? topicId.equals(that.topicId) : that.topicId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (topicId != null ? topicId.hashCode() : 0);
        return result;
    }
}
