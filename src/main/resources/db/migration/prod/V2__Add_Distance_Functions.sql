-- -----------------------------------------------------
-- Function fnDistanceCosine
-- Uses 3963.1676 miles as the equatorial radius based on average elevation
-- -----------------------------------------------------
DROP FUNCTION IF EXISTS fnDistanceCosine;
CREATE FUNCTION fnDistanceCosine(
  latitude1  DOUBLE,
  longitude1 DOUBLE,
  latitude2  DOUBLE,
  longitude2 DOUBLE
)
  RETURNS DOUBLE(16, 12)
DETERMINISTIC
  SQL SECURITY INVOKER
  RETURN ACOS(
             SIN(RADIANS(latitude1)) * SIN(RADIANS(latitude2))
             + COS(RADIANS(latitude1)) * COS(RADIANS(latitude2))
               * COS(RADIANS(longitude2 - longitude1))) * 3963.1676;

-- -----------------------------------------------------
-- Function fnDistanceHaversine
-- Uses 3963.1676 miles as the equatorial radius based on average elevation
-- -----------------------------------------------------
DELIMITER //
DROP FUNCTION IF EXISTS fnDistanceHaversine//
CREATE FUNCTION fnDistanceHaversine(
  latitude1  DOUBLE(15, 12),
  longitude1 DOUBLE(15, 12),
  latitude2  DOUBLE(15, 12),
  longitude2 DOUBLE(15, 12)
)
  RETURNS DOUBLE
DETERMINISTIC
  SQL SECURITY INVOKER
  BEGIN
    DECLARE partial DOUBLE(15, 12);
    SET partial := SIN(RADIANS(latitude2 - latitude1) * 0.5) *
                   SIN(RADIANS(latitude2 - latitude1) * 0.5) +
                   COS(RADIANS(latitude1)) * COS(RADIANS(latitude2)) *
                   SIN(RADIANS(longitude2 - longitude1) * 0.5) *
                   SIN(RADIANS(longitude2 - longitude1) * 0.5);
    RETURN 2.0 * 3963.1676 * ATAN(SQRT(partial), SQRT(1.0 - partial));
  END
//
DELIMITER ;