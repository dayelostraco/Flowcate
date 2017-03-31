-- -----------------------------------------------------
-- Alter `Beacon`
-- -----------------------------------------------------
ALTER TABLE `Beacon`
DROP COLUMN `listingId`,
DROP COLUMN `campaignId`;

-- -----------------------------------------------------
-- Alter `Geofence`
-- -----------------------------------------------------
ALTER TABLE `Geofence`
DROP COLUMN `listingId`,
DROP COLUMN `campaignId`;

-- -----------------------------------------------------
-- Alter `NotificationEvent`
-- -----------------------------------------------------
ALTER TABLE `NotificationEvent`
DROP COLUMN `campaignId`;
