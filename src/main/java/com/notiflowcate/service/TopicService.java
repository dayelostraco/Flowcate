package com.notiflowcate.service;

import com.notiflowcate.exception.TopicException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.TopicDto;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/27/16
 */
public interface TopicService {

    TopicDto registerTopic(ApplicationDto application, TopicDto topic) throws TopicException;

    TopicDto updateTopic(ApplicationDto application, TopicDto topic) throws TopicException;

    void deleteTopic(Long topicId) throws TopicException;

    List<TopicDto> findTopicsByApplication(ApplicationDto application);

    TopicDto findTopicByName(ApplicationDto application, String topicName);

    TopicDto findTopicById(Long topicId);

    List<TopicDto> findAllTopics();
}
