-- -----------------------------------------------------
-- Alter `Notification`
-- -----------------------------------------------------
ALTER TABLE `Notification`
ADD COLUMN `listingId` VARCHAR(64) NULL AFTER `userId`;
