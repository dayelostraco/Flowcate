-- -----------------------------------------------------
-- Alter `DeviceToken`
-- -----------------------------------------------------
ALTER TABLE `DeviceToken`
ADD UNIQUE INDEX `deviceToken_UNIQUE` (`deviceToken` ASC);
