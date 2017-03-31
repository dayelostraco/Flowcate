package com.notiflowcate.repository;

import com.notiflowcate.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component(value = "notificationRepository")
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT COUNT(n) FROM Notification n " +
            "WHERE n.applicationId = :applicationId " +
            "AND n.sentTime BETWEEN :startTime AND :endTime " +
            "AND n.deviceToken IS NULL OR n.deviceToken = :deviceToken")
    Long findUserNotificationsWithinTimeWindow(@Param("applicationId") Long applicationId,
                                               @Param("startTime") LocalDateTime startTime,
                                               @Param("endTime") LocalDateTime endTime,
                                               @Param("deviceToken") String deviceToken);
}
