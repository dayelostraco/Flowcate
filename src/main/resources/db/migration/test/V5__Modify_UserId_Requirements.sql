-- -----------------------------------------------------
-- Alter `DeviceToken`
-- -----------------------------------------------------
ALTER TABLE `DeviceToken`
CHANGE COLUMN `userId` `userId` VARCHAR(64) NULL ;

-- -----------------------------------------------------
-- Alter `NotificationTrigger`
-- -----------------------------------------------------
ALTER TABLE `NotificationTrigger`
CHANGE COLUMN `userId` `userId` VARCHAR(64) NULL ;
