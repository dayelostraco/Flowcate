package com.notiflowcate.repository;

import com.notiflowcate.model.entity.ApplicationNotification;
import com.notiflowcate.model.entity.TopicNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component(value = "applicationNotificationRepository")
public interface ApplicationNotificationRepository extends JpaRepository<ApplicationNotification, Long> {

    @Query("SELECT an FROM ApplicationNotification an WHERE an.applicationId = :applicationId AND an.sentTime BETWEEN :notificationWindowStart AND :notificationWindowEnd")
    List<TopicNotification> findNotificationsWithinSentTimeWindow(@Param("applicationId") Long applicationId,
                                                                  @Param("notificationWindowStart") LocalDateTime notificationWindowStart,
                                                                  @Param("notificationWindowEnd") LocalDateTime notificationWindowEnd);
}
