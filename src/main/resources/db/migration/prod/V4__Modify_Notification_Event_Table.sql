-- -----------------------------------------------------
-- Alter `NotificationEvent`
-- -----------------------------------------------------
ALTER TABLE `NotificationEvent`
CHANGE COLUMN `enter` `onEnter` BIT(1) NOT NULL DEFAULT b'1' ,
CHANGE COLUMN `dwell` `onDwell` BIT(1) NOT NULL DEFAULT b'1' ,
CHANGE COLUMN `exit` `onExit` BIT(1) NOT NULL DEFAULT b'1' ;
