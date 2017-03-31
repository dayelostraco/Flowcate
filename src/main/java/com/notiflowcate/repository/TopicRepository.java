package com.notiflowcate.repository;

import com.notiflowcate.model.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t FROM Topic t WHERE t.topicName = :topicName AND t.deleted IS NULL")
    Topic findTopicByTopicName(@Param("topicName") String topicName);

    @Query("SELECT t FROM Topic t WHERE t.topicName = :topicName AND t.applicationId = :applicationId AND t.deleted IS NULL")
    Topic findTopicByTopicName(@Param("applicationId") Long applicationId, @Param("topicName") String topicName);

    @Query("SELECT t FROM Topic t WHERE t.applicationId = :applicationId AND t.active = true AND t.deleted IS NULL")
    List<Topic> findApplicationTopics(@Param("applicationId") Long applicationId);

    @Query("SELECT t FROM Topic t WHERE t.topicId = :topicId AND t.applicationId = :applicationId AND t.deleted IS NULL")
    Topic findTopicByTopicIdAndApplicationId(@Param("applicationId") Long applicationId, @Param("topicId") Long topicId);

    @Query("SELECT t FROM Topic t WHERE t.topicId = :topicId")
    Topic findTopicByTopicId(@Param("topicId") Long topicId);

    @Query("SELECT t FROM Topic t WHERE t.deleted IS NULL")
    List<Topic> findAllTopics();
}
