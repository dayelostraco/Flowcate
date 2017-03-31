package com.notiflowcate.repository;

import com.notiflowcate.model.entity.GeofenceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GeofenceEventRepository extends JpaRepository<GeofenceEvent, Long> {

    @Query("SELECT ge FROM GeofenceEvent ge WHERE ge.deleted IS NULL")
    List<GeofenceEvent> findAllApplicationGeofenceEvents();

    @Query("SELECT ge FROM GeofenceEvent ge WHERE ge.applicationId = :applicationId AND ge.geofenceId = :geofenceId AND :currentDate BETWEEN ge.startDate AND ge.endDate AND ge.active = true AND ge.deleted IS NULL ORDER BY ge.endDate ASC")
    List<GeofenceEvent> findActiveGeofenceEventByGeofenceId(@Param("applicationId") Long applicationId,
                                                            @Param("geofenceId") Long geofenceId,
                                                            @Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT ge FROM GeofenceEvent ge WHERE ge.applicationId = :applicationId AND ge.notificationEventId = :notificationEventId")
    GeofenceEvent findGeofenceEventByApplicationIdAndNotificationEventId(@Param("applicationId") Long applicationId,
                                                                         @Param("notificationEventId") Long notificationEventId);

    @Query("SELECT ge FROM GeofenceEvent ge WHERE ge.applicationId = :applicationId AND ge.geofenceId = :geofenceId AND ge.deleted IS NULL")
    List<GeofenceEvent> findAllGeofenceEventsByGeofenceId(@Param("applicationId") Long applicationId,
                                                          @Param("geofenceId") Long geofenceId);
}
