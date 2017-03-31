-- -----------------------------------------------------
-- Alter `Beacon`
-- -----------------------------------------------------
ALTER TABLE `Beacon`
ADD COLUMN `listingId` VARCHAR(64) NOT NULL AFTER `applicationId`;

-- -----------------------------------------------------
-- Alter `Geofence`
-- -----------------------------------------------------
ALTER TABLE `Geofence`
ADD COLUMN `listingId` VARCHAR(64) NOT NULL AFTER `applicationId`;

-- -----------------------------------------------------
-- Alter `NotificationEvent`
-- -----------------------------------------------------
ALTER TABLE `NotificationEvent`
ADD COLUMN `listingId` VARCHAR(64) NOT NULL AFTER `applicationId`;
