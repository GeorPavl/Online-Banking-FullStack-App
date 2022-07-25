-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema demo_bank_v2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema demo_bank_v2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `demo_bank_v2` DEFAULT CHARACTER SET utf8 ;
USE `demo_bank_v2` ;

-- -----------------------------------------------------
-- Table `demo_bank_v2`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`user` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `token` VARCHAR(255) NULL,
  `code` INT NULL,
  `verified` TINYINT NOT NULL DEFAULT 0,
  `verified_at` TIMESTAMP NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`account` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `number` VARCHAR(100) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `balance` DECIMAL(18,2) NOT NULL DEFAULT 0.0,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `account_number_UNIQUE` (`number` ASC) VISIBLE,
  UNIQUE INDEX `account_name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_account_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_account_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `demo_bank_v2`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`transaction` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT(20) UNSIGNED NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `amount` DECIMAL(18,2) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `reason_code` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_transaction_account1_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_transaction_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `demo_bank_v2`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`payment` (
  `transaction_id` BIGINT(20) UNSIGNED NOT NULL,
  `beneficiary` VARCHAR(255) NOT NULL,
  `beneficiary_account_number` VARCHAR(255) NOT NULL,
  `reference_number` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`transaction_id`),
  INDEX `fk_payment_transaction1_idx` (`transaction_id` ASC) VISIBLE,
  CONSTRAINT `fk_payment_transaction1`
    FOREIGN KEY (`transaction_id`)
    REFERENCES `demo_bank_v2`.`transaction` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
