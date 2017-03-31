-- -----------------------------------------------------
-- Alter `Beacon`
-- -----------------------------------------------------
ALTER TABLE `Beacon` DROP COLUMN `listingId`;
ALTER TABLE `Beacon` DROP COLUMN `campaignId`;

-- -----------------------------------------------------
-- Alter `Geofence`
-- -----------------------------------------------------
ALTER TABLE `Geofence` DROP COLUMN `listingId`;
ALTER TABLE `Geofence` DROP COLUMN `campaignId`;

-- -----------------------------------------------------
-- Alter `NotificationEvent`
-- -----------------------------------------------------
ALTER TABLE `NotificationEvent` DROP COLUMN `campaignId`;
