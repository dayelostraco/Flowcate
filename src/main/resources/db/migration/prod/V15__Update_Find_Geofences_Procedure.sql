-- -----------------------------------------------------
-- Add `FindNearestGeofences`
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS FindNearestGeofences;
DELIMITER $$
CREATE PROCEDURE FindNearestGeofences(IN userLatitude DECIMAL(10, 8),
                                      IN userLongitude DECIMAL(10, 8),
                                      IN radialDistanceInMiles DECIMAL(17, 12),
                                      IN numResults INTEGER,
                                      IN applicationIdParam INTEGER)
BEGIN
    SET @maxLatitude := userLatitude + DEGREES(radialDistanceInMiles / 3963.1676);
    SET @minLatitude := userLatitude - DEGREES(radialDistanceInMiles / 3963.1676);
    SET @maxLongitude := userLongitude + DEGREES(radialDistanceInMiles / 3963.1676 / COS(RADIANS(userLatitude)));
    SET @minLongitude := userLongitude - DEGREES(radialDistanceInMiles / 3963.1676 / COS(RADIANS(userLatitude)));

    SELECT
      g.geofenceId,
      g.applicationId,
      g.listingId,
      g.name,
      g.description,
      g.latitude,
      g.longitude,
      g.radiusInMeters,
      g.active,
      g.expiration,
      g.deleted,
      g.created,
      g.modified,
      fnDistanceHaversine(g.latitude, g.longitude, userLatitude, userLongitude) AS distanceInMiles
    FROM Geofence g
    INNER JOIN NotificationEvent ne ON ne.geofenceId = g.geofenceId
    WHERE  ((g.latitude BETWEEN @minLatitude AND @maxLatitude) AND (g.longitude BETWEEN @minLongitude AND @maxLongitude))
      AND CURRENT_TIMESTAMP(3) BETWEEN ne.startDate and ne.endDate
      AND ne.active = 1
      AND ne.deleted IS NULL
      AND g.active = 1
      AND g.deleted IS NULL
      AND ((g.expiration IS NULL) OR (g.expiration > CURRENT_TIMESTAMP(3)))
      AND applicationIdParam = g.applicationId
    HAVING distanceInMiles <= radialDistanceInMiles
    ORDER BY distanceInMiles ASC
    LIMIT numResults;
  END;
$$
DELIMITER ;