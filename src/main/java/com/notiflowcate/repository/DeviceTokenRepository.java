package com.notiflowcate.repository;

import com.notiflowcate.model.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    @Query(value = "SELECT dt FROM DeviceToken dt WHERE dt.application.applicationId= :applicationId")
    List<DeviceToken> findAllByApplicationId(@Param("applicationId") Long applicationId);

    @Query(value = "SELECT dt FROM DeviceToken dt WHERE dt.application.applicationId= :applicationId AND dt.deviceTokenId = :deviceTokenId")
    DeviceToken findByDeviceTokenId(@Param("applicationId") Long applicationId, @Param("deviceTokenId") Long deviceTokenId);

    @Query(value = "SELECT dt FROM DeviceToken dt WHERE dt.application.applicationId= :applicationId AND dt.deviceToken = :deviceToken")
    DeviceToken findByDeviceToken(@Param("applicationId") Long applicationId, @Param("deviceToken") String deviceToken);

    @Query("SELECT dt FROM DeviceToken dt WHERE dt.application.applicationId = :applicationId AND dt.userId= :userId")
    List<DeviceToken> findDeviceTokensByApplicationAndAppUser(@Param("applicationId") Long applicationId, @Param("userId") String userId);

    @Query("SELECT dt FROM DeviceToken dt WHERE dt.application.applicationId= :applicationId AND dt.userId IN :userIds")
    List<DeviceToken> findByAppUserIds(@Param("applicationId") Long applicationId, @Param("userIds") List<String> userIds);
}