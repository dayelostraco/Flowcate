package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dayel Ostraco
 * 1/4/16
 */
public class TopicNotificationDto extends NotificationDto {

    @JsonIgnore
    private Long topicId;

    private String topicName;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
