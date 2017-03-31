package com.notiflowcate.repository;

import com.notiflowcate.model.entity.GeofenceTrigger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GeofenceTriggerRepository extends JpaRepository<GeofenceTrigger, Long> {

    @Query("SELECT gt FROM GeofenceTrigger gt WHERE gt.applicationId = :applicationId AND gt.notificationTriggerId = :notificationTriggerId")
    GeofenceTrigger findGeofenceTriggerByApplicationIdAndNotificationTriggerId(@Param("applicationId") Long applicationId,
                                                                               @Param("notificationTriggerId") Long notificationTriggerId);

    @Query("SELECT gt FROM GeofenceTrigger gt WHERE gt.applicationId = :applicationId ORDER BY gt.notificationTriggerId DESC")
    List<GeofenceTrigger> findLatestGeofenceTriggers(@Param("applicationId") Long applicationId, Pageable limit);

    @Query("SELECT gt FROM GeofenceTrigger gt WHERE gt.applicationId = :applicationId AND gt.geofenceId = :geofenceId AND gt.enterTime IS NOT NULL AND gt.enterTime BETWEEN :geofenceWindowStart AND :geofenceWindowEnd ORDER BY gt.enterTime DESC")
    List<GeofenceTrigger> findUsersWithinGeofence(@Param("applicationId") Long applicationId,
                                                  @Param("geofenceId") Long geofenceId,
                                                  @Param("geofenceWindowStart") LocalDateTime geofenceWindowStart,
                                                  @Param("geofenceWindowEnd") LocalDateTime geofenceWindowEnd);
}
