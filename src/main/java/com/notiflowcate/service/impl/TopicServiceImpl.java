package com.notiflowcate.service.impl;

import com.notiflowcate.exception.TopicException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.TopicDto;
import com.notiflowcate.model.entity.Topic;
import com.notiflowcate.repository.TopicRepository;
import com.notiflowcate.service.TopicService;
import com.notiflowcate.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/27/16
 */
@Service("topicService")
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final TransformService orikaTransformService;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository,
                            TransformService orikaTransformService) {

        this.topicRepository = topicRepository;
        this.orikaTransformService = orikaTransformService;
    }

    /**
     * Registers a topic by application and {@link TopicDto}. Checks to see if the provided topic name has
     * already been created and registered with Firebase.
     *
     * @param application {@link ApplicationDto}
     * @param topic       {@link TopicDto}
     * @return Persisted {@link TopicDto}
     * @throws TopicException
     */
    @Override
    public TopicDto registerTopic(ApplicationDto application, TopicDto topic) throws TopicException {

        // Verify that a topic with the provided topicName doesn't already exist
        Topic topicVerification =
                topicRepository.findTopicByTopicName(topic.getTopicName());
        if (topicVerification != null) {
            throw new TopicException("A topic with that name already exists");
        }

        // Convert TopicDto to a Topic entity
        Topic topicEntity = orikaTransformService.map(topic, Topic.class);
        topicEntity.setApplicationId(application.getApplicationId());

        // Persist the Topic
        topicEntity = topicRepository.save(topicEntity);

        // Convert the persisted Topic to a TopicDto
        return orikaTransformService.map(topicEntity, TopicDto.class);
    }

    /**
     * Updates an existing {@link TopicDto} provided that it there is an existing record.
     *
     * @param application {@link ApplicationDto}
     * @param topic       {@link TopicDto}
     * @return Persisted TopicDto
     * @throws TopicException
     */
    @Override
    public TopicDto updateTopic(ApplicationDto application, TopicDto topic) throws TopicException {

        // Verify that the topic exists
        Topic topicVerification =
                topicRepository.findTopicByTopicIdAndApplicationId(application.getApplicationId(), topic.getTopicId());
        if (topicVerification == null) {
            throw new TopicException("Topic is not registered in the system.");
        }

        // Convert TopicDto to a Topic entity
        Topic topicEntity = orikaTransformService.map(topic, Topic.class);
        topicEntity.setCreated(topicVerification.getCreated()); //retain created

        // Persist the Topic
        topicEntity = topicRepository.save(topicEntity);

        // Convert the persisted Topic to a TopicDto
        return orikaTransformService.map(topicEntity, TopicDto.class);
    }

    /**
     * Retrieves all Topics registered with an application.
     *
     * @param application {@link ApplicationDto}
     * @return Collection of application TopicDto
     */
    @Override
    public List<TopicDto> findTopicsByApplication(ApplicationDto application) {
        return orikaTransformService.mapList(topicRepository.findApplicationTopics(application.getApplicationId()), TopicDto.class);
    }

    /**
     * Retrieves a unique {@link TopicDto} but it's assigned topic name. Verifies that the API caller has access
     * to the {@link TopicDto}.
     *
     * @param application {@link ApplicationDto}
     * @param topicName   {@link String}
     * @return Retrieved {@link TopicDto} or null if it does not exist
     */
    @Override
    public TopicDto findTopicByName(ApplicationDto application, String topicName) {
        return orikaTransformService.map(topicRepository.findTopicByTopicName(application.getApplicationId(), topicName), TopicDto.class);
    }

    /**
     * Retrieves a {@link TopicDto} by its Primary Key id. Ensures the the API caller has access to the requested
     * {@link TopicDto}.
     *
     * @param topicId     {@link Long}
     * @return
     */
    @Override
    public TopicDto findTopicById(Long topicId) {
        return orikaTransformService.map(topicRepository.findTopicByTopicId(topicId), TopicDto.class);
    }

    @Override
    public List<TopicDto> findAllTopics() {
        return orikaTransformService.mapList(topicRepository.findAllTopics(), TopicDto.class);
    }

    /**
     * Soft deletes a {@link TopicDto} by its Primary Key id. Ensures the the API caller has access to the requested.
     *
     * @param topicId {@link Long}
     * @throws TopicException
     */
    @Override
    public void deleteTopic(Long topicId) throws TopicException {

        // Retrieve Topic
        Topic topicVerification = topicRepository.findTopicByTopicId(topicId);
        if(topicVerification==null) {
            throw new TopicException("Topic does not exist.");
        }

        // Soft Delete
        topicVerification.setDeleted(LocalDateTime.now(ZoneId.of("UTC")));

        // Persist soft deleted Topic
        topicRepository.save(topicVerification);
    }
}
