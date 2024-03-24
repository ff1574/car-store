DROP DATABASE IF EXISTS cardatabase;
CREATE DATABASE IF NOT EXISTS cardatabase;
USE cardatabase;

DROP TABLE IF EXISTS manufacturers;
CREATE TABLE IF NOT EXISTS manufacturers
  (
     manufacturer_id        INT AUTO_INCREMENT  PRIMARY KEY,
     manufacturer_name      VARCHAR(255)        NOT NULL,
     manufacturer_country   VARCHAR(100),
     manufacturer_website   VARCHAR(255)
  );

DROP TABLE IF EXISTS cars;
CREATE TABLE IF NOT EXISTS cars
  (
     car_id                 INT AUTO_INCREMENT  PRIMARY KEY,
     manufacturer_id        INT,
     car_model              VARCHAR(255)        NOT NULL,
     car_year               YEAR,
     car_price              DECIMAL(10, 2),
     car_color              VARCHAR(50),
     car_engine             VARCHAR(100),
     car_stock_quantity     INT,

     FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(manufacturer_id)
  );

DROP TABLE IF EXISTS customers;
CREATE TABLE IF NOT EXISTS customers
  (
     customer_id            INT AUTO_INCREMENT  PRIMARY KEY,
     customer_name          VARCHAR(255)        NOT NULL,
     customer_email         VARCHAR(255)		UNIQUE,
     customer_phone         VARCHAR(50),
     customer_address       TEXT
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
  );

DROP TABLE IF EXISTS order_details;
CREATE TABLE IF NOT EXISTS order_details
  (
     order_detail_id        INT AUTO_INCREMENT  PRIMARY KEY,
     order_id               INT,
     car_id                 INT,
     car_quantity           INT,
     car_price_per_unit     DECIMAL(10, 2),

     FOREIGN KEY (order_id) REFERENCES orders(order_id),
     FOREIGN KEY (car_id) REFERENCES cars(car_id)
  );
  
DROP TABLE IF EXISTS administrators;
CREATE TABLE IF NOT EXISTS administrators
  (
     administrator_id       INT AUTO_INCREMENT  PRIMARY KEY,
     administrator_name     VARCHAR(255)        NOT NULL,
     administrator_email    VARCHAR(255)        NOT NULL UNIQUE,
     administrator_password VARCHAR(255)        NOT NULL
  );

-- Inserting into Manufacturers
INSERT INTO manufacturers (manufacturer_name, manufacturer_country, manufacturer_website) VALUES
('Tesla', 'USA', 'https://www.tesla.com'),
('Toyota', 'Japan', 'https://www.toyota.com'),
('BMW', 'Germany', 'https://www.bmw.com');

-- Inserting into Cars
INSERT INTO cars (manufacturer_id, car_model, car_year, car_price, car_color, car_engine, car_stock_quantity) VALUES
(1, 'Model S', 2022, 79990, 'Red', 'Electric', 5),
(2, 'Corolla', 2021, 20000, 'Blue', 'Gasoline', 10),
(3, 'Model 3', 2022, 58900, 'Black', 'Diesel', 4);

-- Inserting into Customers
INSERT INTO customers (customer_name, customer_email, customer_phone, customer_address) VALUES
('John Doe', 'johndoe@example.com', '555-0101', '123 Elm Street, Springfield'),
('Jane Smith', 'janesmith@example.com', '555-0102', '456 Oak Street, Metropolis');

-- Inserting into Orders
INSERT INTO orders (customer_id, order_date, order_total) VALUES
(1, '2023-03-24', 79990),
(2, '2023-03-25', 20000);

-- Inserting into OrderDetails
INSERT INTO order_details (order_id, car_id, car_quantity, car_price_per_unit) VALUES
(1, 1, 1, 79990),
(2, 2, 1, 20000);

-- Inserting into Administrators
INSERT INTO administrators (administrator_name, administrator_email, administrator_password) VALUES
('Franko Fišter', 'ff1574@rit.edu', SHA2('admin', 256)),
('Denny Lulak', 'dal4933@rit.edu', SHA2('admin', 256)),
('Mladen Orsulic', 'mo4674@rit.edu', SHA2('admin', 256));


