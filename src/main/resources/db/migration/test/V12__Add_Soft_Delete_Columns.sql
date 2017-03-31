-- -----------------------------------------------------
-- Alter `Application`
-- -----------------------------------------------------
ALTER TABLE `Application`
ADD COLUMN `deleted` TIMESTAMP NULL DEFAULT NULL AFTER `active`;

-- -----------------------------------------------------
-- Alter `Beacon`
-- -----------------------------------------------------
ALTER TABLE `Beacon`
ADD COLUMN `deleted` TIMESTAMP NULL DEFAULT NULL AFTER `active`;

-- -----------------------------------------------------
-- Alter `Geofence`
-- -----------------------------------------------------
ALTER TABLE `Geofence`
ADD COLUMN `deleted` TIMESTAMP NULL DEFAULT NULL AFTER `expiration`;

-- -----------------------------------------------------
-- Alter `NotificationEvent`
-- -----------------------------------------------------
ALTER TABLE `NotificationEvent`
ADD COLUMN `deleted` TIMESTAMP NULL DEFAULT NULL AFTER `geofenceId`;

-- -----------------------------------------------------
-- Alter `Topic`
-- -----------------------------------------------------
ALTER TABLE `Topic`
ADD COLUMN `deleted` TIMESTAMP NULL DEFAULT NULL AFTER `active`;
