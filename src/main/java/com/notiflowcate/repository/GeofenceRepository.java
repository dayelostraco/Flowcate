package com.notiflowcate.repository;

import com.notiflowcate.model.entity.Geofence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GeofenceRepository extends JpaRepository<Geofence, Long> {

    @Override
//    @Caching(evict = {
//            @CacheEvict(value = "geofences", key = "'Application'.concat(#p0.applicationId.toString()).concat('-Geofences')"),
//            @CacheEvict(value = "geofences", key = "#p0.geofenceId", condition = "#p0.geofenceId!=null")
//    })
    <S extends Geofence> S save(S geofence);

    //    @Cacheable(value = "geofences", key = "'Application'.concat(#p0.toString()).concat('-Geofences')")
    @Query("SELECT g FROM Geofence g WHERE g.applicationId = :applicationId AND g.deleted IS NULL")
    List<Geofence> findGeofencesByApplication(@Param("applicationId") Long applicationId);

    //    @Cacheable(value = "geofences", key = "#p1")
    @Query("SELECT g FROM Geofence g WHERE g.applicationId = :applicationId AND g.geofenceId = :geofenceId")
    Geofence findGeofenceByApplicationIdAndGeofenceId(@Param("applicationId") Long applicationId,
                                                      @Param("geofenceId") Long geofenceId);

    @Query("SELECT g FROM Geofence g WHERE g.applicationId = :applicationId AND g.listingId = :listingId AND g.deleted IS NULL")
    List<Geofence> findGeofencesByListing(@Param("applicationId") Long applicationId,
                                          @Param("listingId") String listingId);

    @Query(nativeQuery = true, value = "CALL FindNearestGeofences(:latitude, :longitude, :maxRadius, :maxResult, :applicationId)")
    List<Geofence> findGeofencesByLocation(@Param("applicationId") Long applicationId,
                                           @Param("latitude") Double latitude,
                                           @Param("longitude") Double longitude,
                                           @Param("maxRadius") Double maxRadius,
                                           @Param("maxResult") Integer maxResult);
}
