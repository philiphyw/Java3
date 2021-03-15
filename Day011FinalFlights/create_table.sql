-- Create finaldb
CREATE SCHEMA `finaldb` DEFAULT CHARACTER SET utf8mb4 ;

USE finaldb;

-- Create flights table
CREATE TABLE `finaldb`.`flights` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `onDate` DATE NOT NULL,
  `fromCode` VARCHAR(5) NOT NULL,
  `toCode` VARCHAR(5) NOT NULL,
  `type` ENUM("Domestic", "International", "Private") NOT NULL,
  `passengers` INT NOT NULL,
  PRIMARY KEY (`id`))
  ;
  
 -- Insert dummy data
 INSERT INTO finaldb.flights (`onDate`, `fromCode`, `toCode`, `type`, passengers) 
	VALUES 
    ("2021-01-23", 'DDC', 'DAF', 'International', 200),
    ("2021-02-11", 'ASD', 'DAF', 'International', 200),
    ("2021-03-25", 'ADF', 'DDC', 'Private', 20),
	("2021-03-30", 'SSDS', 'YUIO', 'International', 150),
	("2021-04-12", 'ASD', 'DAF', 'International', 200),
	("2021-05-02", 'ADFF', 'BHJN', 'Domestic', 100),
    ("2021-07-02", 'DAF', 'SSDS', 'Domestic', 100)
;
 
 
 
select * from flights;
