DROP DATABASE IF EXISTS cardatabase;
CREATE DATABASE IF NOT EXISTS cardatabase;
USE cardatabase;

DROP TABLE IF EXISTS manufacturers;
CREATE TABLE manufacturers
  (
     manufacturerid INT auto_increment PRIMARY KEY,
     name           VARCHAR(255) NOT NULL,
     country        VARCHAR(100),
     website        VARCHAR(255)
  );

DROP TABLE IF EXISTS cars;
CREATE TABLE cars
  (
     carid          INT auto_increment PRIMARY KEY,
     manufacturerid INT,
     model          VARCHAR(255) NOT NULL,
     year           YEAR,
     price          DECIMAL(10, 2),
     color          VARCHAR(50),
     enginetype     VARCHAR(100),
     stockquantity  INT,
     FOREIGN KEY (manufacturerid) REFERENCES manufacturers(manufacturerid)
  );

DROP TABLE IF EXISTS customers;
CREATE TABLE customers
  (
     customerid INT auto_increment PRIMARY KEY,
     firstname  VARCHAR(255) NOT NULL,
     lastname   VARCHAR(255) NOT NULL,
     email      VARCHAR(255),
     phone      VARCHAR(50),
     address    TEXT
  );
DROP TABLE IF EXISTS orders;
CREATE TABLE orders
  (
     orderid    INT auto_increment PRIMARY KEY,
     customerid INT,
     orderdate  DATE,
     totalprice DECIMAL(10, 2) NULL,
     FOREIGN KEY (customerid) REFERENCES customers(customerid)
  );

DROP TABLE IF EXISTS orderdetails;
CREATE TABLE orderdetails
  (
     orderdetailid INT auto_increment PRIMARY KEY,
     orderid       INT,
     carid         INT,
     quantity      INT,
     priceperunit  DECIMAL(10, 2),
     FOREIGN KEY (orderid) REFERENCES orders(orderid),
     FOREIGN KEY (carid) REFERENCES cars(carid)
  );

-- Inserting into Manufacturers
INSERT INTO Manufacturers (Name, Country, Website) VALUES
('Tesla', 'USA', 'https://www.tesla.com'),
('Toyota', 'Japan', 'https://www.toyota.com'),
('BMW', 'Germany', 'https://www.bmw.com');

-- Inserting into Cars
INSERT INTO Cars (ManufacturerID, Model, Year, Price, Color, EngineType, StockQuantity) VALUES
(1, 'Model S', 2022, 79990, 'Red', 'Electric', 5),
(2, 'Corolla', 2021, 20000, 'Blue', 'Gasoline', 10),
(3, 'X5', 2022, 58900, 'Black', 'Diesel', 4);

-- Inserting into Customers
INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES
('John', 'Doe', 'johndoe@example.com', '555-0101', '123 Elm Street, Springfield'),
('Jane', 'Smith', 'janesmith@example.com', '555-0102', '456 Oak Street, Metropolis');

-- Inserting into Orders
INSERT INTO Orders (CustomerID, OrderDate, TotalPrice) VALUES
(1, '2023-03-24', 79990),
(2, '2023-03-25', 20000);

-- Inserting into OrderDetails
INSERT INTO OrderDetails (OrderID, CarID, Quantity, PricePerUnit) VALUES
(1, 1, 1, 79990),
(2, 2, 1, 20000);

