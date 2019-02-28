#!/usr/bin/env bash

docker exec -i $1 mysql -uroot -padmin << EOF
DELIMITER //
SET GLOBAL time_zone = '-3:00';
SET time_zone = '-3:00';
CREATE database karate;
    //
DELIMITER ;
EOF

docker exec -i $1 mysql -uroot -padmin karate << EOF
DELIMITER //
CREATE USER wtropen@'%' IDENTIFIED BY 'sita';
GRANT ALL PRIVILEGES ON *.* TO 'wtropen'@'%';
FLUSH PRIVILEGES;
//
DELIMITER ;
EOF

docker exec -i $1 mysql -uroot -padmin karate << EOF
DELIMITER //
DROP TABLE greeting;
//
DELIMITER ;
EOF

docker exec -i $1 mysql -uroot -padmin karate << EOF
DELIMITER //
CREATE TABLE greeting(
name varchar(255),
greet varchar(255),
lastUpdated datetime,
fromStoredProcedure varchar(255)
)//
DELIMITER ;
EOF

#docker exec -i $1 mysql -uroot -padmin karate << EOF
#DELIMITER //
#CREATE PROCEDURE update_greeting(
#  IN personName VARCHAR(255),
#  OUT result BOOLEAN)
#MODIFIES SQL DATA
#BEGIN ATOMIC
#  UPDATE greeting g SET g.lastUpdated = NOW() WHERE g.name = personName;
#  UPDATE greeting g
#    SET g.fromStoredProcedure = CONCAT(g.name, ' says FROM TRIGGER [', g.fromStoredProcedure, '] at ', DATE_FORMAT(g.lastUpdated, '%d %m %Y'))
#  WHERE g.name = personName;
#END;
#//
#DELIMITER ;
#EOF

docker exec -i $1 mysql -uroot -padmin karate << EOF
DELIMITER //
CREATE PROCEDURE update_greeting(
  IN personName VARCHAR(255))
MODIFIES SQL DATA
BEGIN
  UPDATE greeting g
    SET
      g.fromStoredProcedure = CONCAT(g.name, ' says FROM PROCEDURE [', g.greet, '] at ', DATE_FORMAT(g.lastUpdated, '%d/%m/%Y %H:%i:%s')),
      g.lastUpdated = NOW()
  WHERE g.name = personName;
END;
//
DELIMITER ;
EOF

docker exec -i $1 mysql -uroot -padmin karate << EOF
DELIMITER //
CREATE TRIGGER updateGreetingTrigger BEFORE INSERT ON greeting FOR EACH ROW
BEGIN
  SET NEW.lastUpdated = NOW();
  SET NEW.greet = NEW.fromStoredProcedure;
  SET NEW.fromStoredProcedure = CONCAT(NEW.name, ' says FROM TRIGGER [', NEW.fromStoredProcedure, '] at ', DATE_FORMAT(NEW.lastUpdated, '%d/%m/%Y %H:%i:%s'));
END;
//
DELIMITER ;
EOF