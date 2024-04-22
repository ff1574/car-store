DROP DATABASE IF EXISTS cardatabase;
CREATE DATABASE IF NOT EXISTS cardatabase;
USE cardatabase;

DROP TABLE IF EXISTS manufacturers;
CREATE TABLE IF NOT EXISTS manufacturers
  (
     manufacturer_id        INT AUTO_INCREMENT  PRIMARY KEY,
     manufacturer_name      VARCHAR(255)        NOT NULL,
     manufacturer_image		BLOB,
     manufacturer_country   VARCHAR(100),
     manufacturer_website   VARCHAR(255)
  );

DROP TABLE IF EXISTS cars;
CREATE TABLE IF NOT EXISTS cars
  (
     car_id                 INT AUTO_INCREMENT  PRIMARY KEY,
     manufacturer_id        INT,
     car_model              VARCHAR(255)        NOT NULL,
     car_image 				BLOB,
     car_year               YEAR,
     car_mileage			INT,
     car_price              DECIMAL(10, 2),
     car_color              VARCHAR(50),
     car_engine             VARCHAR(100),
     car_stock_quantity     INT,

     FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(manufacturer_id)
        ON DELETE CASCADE ON UPDATE CASCADE
  );

DROP TABLE IF EXISTS customers;
CREATE TABLE IF NOT EXISTS customers
  (
     customer_id            INT AUTO_INCREMENT  PRIMARY KEY,
     customer_name          VARCHAR(255)        NOT NULL,
     customer_email         VARCHAR(255)		UNIQUE,
     customer_password 		VARCHAR(255)        NOT NULL,
     customer_phone         VARCHAR(50)
  );
  
DROP TABLE IF EXISTS customer_adresses;
CREATE TABLE IF NOT EXISTS customer_addresses
(
    customer_address_id           	INT AUTO_INCREMENT PRIMARY KEY,
    customer_id          			INT,
    customer_address_line1        	VARCHAR(255) NOT NULL,
    customer_address_line2        	VARCHAR(255),
    customer_city                 	VARCHAR(100) NOT NULL,
    customer_state_or_province    	VARCHAR(100),
    customer_postal_code          	VARCHAR(20),
    customer_country              	VARCHAR(100) NOT NULL,
    
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
  
DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders
  (
     order_id               INT AUTO_INCREMENT  PRIMARY KEY,
     customer_id            INT,
     order_date             DATE,
     order_total            DECIMAL(10, 2)      NULL,
     order_status			ENUM('Completed', 'In Progress', 'Cancelled'),

     FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
        ON DELETE CASCADE ON UPDATE CASCADE
  );

DROP TABLE IF EXISTS order_details;
CREATE TABLE IF NOT EXISTS order_details
  (
     order_detail_id        INT AUTO_INCREMENT  PRIMARY KEY,
     order_id               INT,
     car_id                 INT,
     car_quantity           INT,
     car_price_per_unit     DECIMAL(10, 2),

     FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (car_id) REFERENCES cars(car_id)
        ON DELETE CASCADE ON UPDATE CASCADE
  );
  
DROP TABLE IF EXISTS administrators;
CREATE TABLE IF NOT EXISTS administrators
  (
     administrator_id       INT AUTO_INCREMENT  PRIMARY KEY,
     administrator_name     VARCHAR(255)        NOT NULL,
     administrator_email    VARCHAR(255)        NOT NULL UNIQUE,
     administrator_password VARCHAR(60)
  );

-- Inserting into Manufacturers
INSERT INTO manufacturers (manufacturer_name, manufacturer_country, manufacturer_website) VALUES
('Tesla', 'USA', 'https://www.tesla.com'),
('Toyota', 'Japan', 'https://www.toyota.com'),
('BMW', 'Germany', 'https://www.bmw.com'),
('Ford', 'USA', 'https://www.ford.com'),
('Volkswagen', 'Germany', 'https://www.volkswagen.com'),
('Hyundai', 'South Korea', 'https://www.hyundai.com'),
('Fiat', 'Italy', 'https://www.fiat.com'),
('Renault', 'France', 'https://www.renault.com');

-- Inserting into Cars
-- Tesla
INSERT INTO cars (manufacturer_id, car_model, car_year, car_price, car_color, car_engine, car_mileage, car_stock_quantity) VALUES
(1, 'Model S', 2022, 79990, 'Red', 'Electric', 5000, 5),
(1, 'Model 3', 2022, 58900, 'Black', 'Electric', 3000, 4),
(1, 'Model X', 2021, 89990, 'White', 'Electric', 8000, 3),
(1, 'Model Y', 2021, 53990, 'Blue', 'Electric', 2000, 6),
(1, 'Cybertruck', 2023, 39900, 'Silver', 'Electric', 100, 2),
(1, 'Roadster', 2022, 200000, 'Red', 'Electric', 1500, 1);

-- Toyota
INSERT INTO cars (manufacturer_id, car_model, car_year, car_price, car_color, car_engine, car_mileage, car_stock_quantity) VALUES
(2, 'Corolla', 2021, 20000, 'Blue', 'Gasoline', 15000, 10),
(2, 'Camry', 2022, 25000, 'Black', 'Gasoline', 12000, 8),
(2, 'Prius', 2021, 24000, 'White', 'Hybrid', 18000, 5),
(2, 'RAV4', 2022, 26000, 'Silver', 'Gasoline', 10000, 7),
(2, 'Highlander', 2021, 35000, 'Red', 'Gasoline', 20000, 4),
(2, 'Tacoma', 2022, 32000, 'Black', 'Gasoline', 8000, 6);

-- BMW
INSERT INTO cars (manufacturer_id, car_model, car_year, car_price, car_color, car_engine, car_mileage, car_stock_quantity) VALUES
(3, '3 Series', 2022, 41000, 'Black', 'Gasoline', 5000, 6),
(3, '5 Series', 2021, 53000, 'Grey', 'Gasoline', 8000, 4),
(3, 'X5', 2022, 59000, 'White', 'Diesel', 3000, 5),
(3, 'X3', 2021, 43000, 'Blue', 'Diesel', 10000, 7),
(3, 'i8', 2022, 147000, 'Silver', 'Hybrid', 2000, 2),
(3, 'Z4', 2021, 49000, 'Red', 'Gasoline', 4000, 3);

-- Ford
INSERT INTO cars (manufacturer_id, car_model, car_year, car_price, car_color, car_engine, car_mileage, car_stock_quantity) VALUES
(4, 'F-150', 2022, 30000, 'Black', 'Gasoline', 8000, 8),
(4, 'Mustang', 2021, 35000, 'Red', 'Gasoline', 5000, 5),
(4, 'Explorer', 2022, 40000, 'Blue', 'Gasoline', 6000, 4),
(4, 'Escape', 2021, 25000, 'White', 'Gasoline', 10000, 7),
(4, 'Bronco', 2023, 45000, 'Orange', 'Gasoline', 2000, 3),
(4, 'Edge', 2022, 32000, 'Grey', 'Gasoline', 4000, 6);

-- Volkswagen
INSERT INTO cars (manufacturer_id, car_model, car_year, car_price, car_color, car_engine, car_mileage, car_stock_quantity) VALUES
(5, 'Golf', 2021, 23000, 'White', 'Gasoline', 12000, 9),
(5, 'Passat', 2022, 28000, 'Black', 'Diesel', 8000, 7),
(5, 'Tiguan', 2021, 34000, 'Blue', 'Gasoline', 15000, 5),
(5, 'Arteon', 2022, 38000, 'Silver', 'Gasoline', 3000, 4),
(5, 'Touareg', 2021, 50000, 'Grey', 'Diesel', 10000, 3),
(5, 'Polo', 2022, 20000, 'Red', 'Gasoline', 6000, 8);

-- Hyundai
INSERT INTO cars (manufacturer_id, car_model, car_year, car_price, car_color, car_engine, car_mileage, car_stock_quantity) VALUES
(6, 'Sonata', 2021, 27000, 'Silver', 'Gasoline', 10000, 10),
(6, 'Elantra', 2022, 20000, 'Black', 'Gasoline', 7000, 8),
(6, 'Tucson', 2021, 32000, 'Blue', 'Gasoline', 8000, 7),
(6, 'Santa Fe', 2022, 35000, 'White', 'Gasoline', 5000, 5),
(6, 'Palisade', 2021, 45000, 'Grey', 'Gasoline', 4000, 4),
(6, 'Kona', 2022, 21000, 'Red', 'Electric', 2000, 6);

-- Fiat
INSERT INTO cars (manufacturer_id, car_model, car_year, car_price, car_color, car_engine, car_mileage, car_stock_quantity) VALUES
(7, '500', 2021, 17000, 'Red', 'Gasoline', 15000, 12),
(7, 'Panda', 2022, 15000, 'White', 'Gasoline', 12000, 10),
(7, 'Tipo', 2021, 20000, 'Grey', 'Diesel', 18000, 8),
(7, '500X', 2022, 25000, 'Black', 'Gasoline', 10000, 7),
(7, '124 Spider', 2021, 28000, 'Blue', 'Gasoline', 9000, 5),
(7, '500L', 2022, 22000, 'Yellow', 'Gasoline', 6000, 9);

-- Renault
INSERT INTO cars (manufacturer_id, car_model, car_year, car_price, car_color, car_engine, car_mileage, car_stock_quantity) VALUES
(8, 'Clio', 2021, 18000, 'Blue', 'Gasoline', 20000, 11),
(8, 'Megane', 2022, 22000, 'Black', 'Gasoline', 15000, 9),
(8, 'Kadjar', 2021, 26000, 'White', 'Gasoline', 18000, 7),
(8, 'Captur', 2022, 24000, 'Orange', 'Gasoline', 13000, 8),
(8, 'Zoe', 2021, 35000, 'Red', 'Electric', 3000, 6),
(8, 'Talisman', 2022, 33000, 'Grey', 'Gasoline', 9000, 4);


-- Inserting into Customers
INSERT INTO customers (customer_name, customer_email, customer_password, customer_phone) VALUES
('John Doe', 'johndoe@example.com', SHA2('johnny', 256), '555-0101'),
('Jane Smith', 'janesmith@example.com', SHA2('jane', 256), '555-0102');

INSERT INTO customer_addresses (customer_id, customer_address_line1, customer_address_line2, customer_city, customer_state_or_province, customer_postal_code, customer_country) VALUES
(1, '123 Elm Street', '', 'Springfield', 'StateName', '12345', 'USA'),
(2, '456 Oak Street', 'Apt 2', 'Metropolis', 'StateName', '67890', 'USA');

-- Inserting into Orders
INSERT INTO orders (customer_id, order_date, order_total) VALUES
(1, '2023-03-24', 79990),
(2, '2023-03-25', 20000);

-- Inserting into OrderDetails
INSERT INTO order_details (order_id, car_id, car_quantity, car_price_per_unit) VALUES
(1, 1, 1, 79990),
(2, 2, 1, 20000);

-- Inserting into Administrators
-- The passwords are written here so you can access any of the profiles
INSERT INTO administrators (administrator_name, administrator_email) VALUES
('Franko Fišter', 'ff1574@rit.edu'),
('Denny Lulak', 'dal4933@rit.edu'),
('Mladen Oršulić', 'mo4674@rit.edu'),
('Dorian Dražić-Karalić', 'dd9349@rit.edu'),
('Branko Mihaljević', 'bxmcada@rit.edu');

SELECT * FROM administrators;
SELECT * FROM manufacturers;
DESCRIBE manufacturers;
SHOW FIELDS FROM manufacturers WHERE Field = 'manufacturer_image';
SELECT manufacturer_image FROM manufacturers WHERE manufacturer_id = 1;

SELECT manufacturer_id, HEX(manufacturer_image) AS image_hex FROM manufacturers;

SELECT * FROM cars WHERE manufacturer_id = 1;