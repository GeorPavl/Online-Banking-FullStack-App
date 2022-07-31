SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema demo_bank_v2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `demo_bank_v2` DEFAULT CHARACTER SET utf8 ;
USE `demo_bank_v2` ;

-- -----------------------------------------------------
-- Table `demo_bank_v2`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`users` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(250) NOT NULL,
  `enabled` TINYINT NOT NULL,
  `verified` TINYINT NOT NULL,
  `verified_at` TIMESTAMP NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` VARCHAR(45) NOT NULL DEFAULT 'CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP',
  `token` VARCHAR(250) NULL,
  `code` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`roles` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`users_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`users_roles` (
  `users_id` BIGINT(20) UNSIGNED NOT NULL,
  `roles_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`users_id`, `roles_id`),
  INDEX `fk_users_has_roles_roles1_idx` (`roles_id` ASC) VISIBLE,
  INDEX `fk_users_has_roles_users_idx` (`users_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_has_roles_users`
    FOREIGN KEY (`users_id`)
    REFERENCES `demo_bank_v2`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_roles_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `demo_bank_v2`.`roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`persons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`persons` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(250) NOT NULL,
  `users_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_persons_users1_idx` (`users_id` ASC) VISIBLE,
  CONSTRAINT `fk_persons_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `demo_bank_v2`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`accounts` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `users_id` BIGINT(20) UNSIGNED NOT NULL,
  `number` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `balance` DECIMAL(18,2) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE,
  INDEX `fk_accounts_users1_idx` (`users_id` ASC) VISIBLE,
  CONSTRAINT `fk_accounts_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `demo_bank_v2`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`transactions` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `accounts_id` BIGINT(20) UNSIGNED NOT NULL,
  `amount` DOUBLE NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `reason_code` VARCHAR(250) NULL,
  `source` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_transactions_accounts1_idx` (`accounts_id` ASC) VISIBLE,
  CONSTRAINT `fk_transactions_accounts1`
    FOREIGN KEY (`accounts_id`)
    REFERENCES `demo_bank_v2`.`accounts` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`payments` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `transactions_id` BIGINT(20) UNSIGNED NOT NULL,
  `beneficiary` VARCHAR(250) NOT NULL,
  `beneficiary_account_number` VARCHAR(250) NOT NULL,
  `reference_number` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_payments_transactions1_idx` (`transactions_id` ASC) VISIBLE,
  CONSTRAINT `fk_payments_transactions1`
    FOREIGN KEY (`transactions_id`)
    REFERENCES `demo_bank_v2`.`transactions` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
