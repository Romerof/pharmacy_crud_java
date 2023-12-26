-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema pharmacy_database
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pharmacy_database
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pharmacy_database` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `pharmacy_database` ;

-- -----------------------------------------------------
-- Table `pharmacy_database`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pharmacy_database`.`categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `updated` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `pharmacy_database`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pharmacy_database`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(60) NULL DEFAULT NULL,
  `address` VARCHAR(60) NULL DEFAULT NULL,
  `telephone` VARCHAR(20) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `updated` DATETIME NULL DEFAULT NULL,
  `doc_id` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `customerscol_UNIQUE` (`doc_id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `pharmacy_database`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pharmacy_database`.`employees` (
  `id` INT NOT NULL,
  `full_name` VARCHAR(60) NULL DEFAULT NULL,
  `username` VARCHAR(60) NULL DEFAULT NULL,
  `address` VARCHAR(60) NULL DEFAULT NULL,
  `telephone` VARCHAR(20) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `rol` VARCHAR(20) NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `updated` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `pharmacy_database`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pharmacy_database`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(60) NULL DEFAULT NULL,
  `name` VARCHAR(60) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `price` DOUBLE NULL DEFAULT NULL,
  `quantity` INT NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `updated` DATETIME NULL DEFAULT NULL,
  `id_category` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
  INDEX `product_category_idx` (`id_category` ASC) VISIBLE,
  CONSTRAINT `product_category`
    FOREIGN KEY (`id_category`)
    REFERENCES `pharmacy_database`.`categories` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '				';


-- -----------------------------------------------------
-- Table `pharmacy_database`.`sales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pharmacy_database`.`sales` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `document` VARCHAR(45) NULL DEFAULT NULL,
  `id_employee` INT NULL DEFAULT NULL,
  `id_customer` INT NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `updated` DATETIME NULL DEFAULT NULL,
  `total` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `_idx` (`id_customer` ASC) VISIBLE,
  INDEX `sales_employee_idx` (`id_employee` ASC) VISIBLE,
  CONSTRAINT `sales_customer`
    FOREIGN KEY (`id_customer`)
    REFERENCES `pharmacy_database`.`customers` (`id`),
  CONSTRAINT `sales_employee`
    FOREIGN KEY (`id_employee`)
    REFERENCES `pharmacy_database`.`employees` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `pharmacy_database`.`products_sales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pharmacy_database`.`products_sales` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_product` INT NULL DEFAULT NULL,
  `amount` INT NULL DEFAULT NULL,
  `price` DOUBLE NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `updated` DATETIME NULL DEFAULT NULL,
  `id_sale` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `sale_product_idx` (`id_product` ASC) VISIBLE,
  INDEX `product_sale_idx` (`id_sale` ASC) VISIBLE,
  CONSTRAINT `product_sale`
    FOREIGN KEY (`id_sale`)
    REFERENCES `pharmacy_database`.`sales` (`id`),
  CONSTRAINT `sale_product`
    FOREIGN KEY (`id_product`)
    REFERENCES `pharmacy_database`.`products` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 43
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `pharmacy_database`.`suppliers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pharmacy_database`.`suppliers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `telephone` VARCHAR(20) NULL DEFAULT NULL,
  `address` VARCHAR(60) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `city` VARCHAR(60) NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `updated` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `pharmacy_database`.`purchases`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pharmacy_database`.`purchases` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total` DOUBLE NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `supplier_id` INT NULL DEFAULT NULL,
  `employee_id` INT NULL DEFAULT NULL,
  `num_document` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `num_document_UNIQUE` (`num_document` ASC) VISIBLE,
  INDEX `purchases_suppliers_idx` (`supplier_id` ASC) VISIBLE,
  INDEX `purchases_employees_idx` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `purchases_employees`
    FOREIGN KEY (`employee_id`)
    REFERENCES `pharmacy_database`.`employees` (`id`),
  CONSTRAINT `purchases_suppliers`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `pharmacy_database`.`suppliers` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `pharmacy_database`.`purchase_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pharmacy_database`.`purchase_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `purchase_price` DOUBLE NULL DEFAULT NULL,
  `purchase_amount` INT NULL DEFAULT NULL,
  `purchase_sub` DOUBLE NULL DEFAULT NULL,
  `purchase_id` INT NULL DEFAULT NULL,
  `product_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `purchase_details_purchase_idx` (`purchase_id` ASC) VISIBLE,
  INDEX `purchase_details_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `purchase_details_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `pharmacy_database`.`products` (`id`),
  CONSTRAINT `purchase_details_purchase`
    FOREIGN KEY (`purchase_id`)
    REFERENCES `pharmacy_database`.`purchases` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `pharmacy_database`.`employees` (`id`, `full_name`, `username`, `password`) VALUES ('1', 'root', 'root', 'root');
INSERT INTO `pharmacy_database`.`categories` (`name` ) VALUES ('Sin categoria')