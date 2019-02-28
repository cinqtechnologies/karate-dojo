use karate;

CREATE USER wtropen@'%' IDENTIFIED BY 'sita';
GRANT ALL PRIVILEGES ON *.* TO 'wtropen'@'%';
FLUSH PRIVILEGES;

DELIMITER //
CREATE PROCEDURE update_greetiing(
  IN personName VARCHAR(255)
)
BEGIN
  UPDATE greeting g
    SET lastUpdated = NOW()
  WHERE g.name = personName;
  UPDATE greeting g
    SET g.fromStoredProcedure = g.name + ' says [' + g.fromStoredProcedure + '] at ' + g.lastUpdated
  WHERE g.name = personName;
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER myTrigger BEFORE INSERT ON employee
  FOR EACH ROW
BEGIN
  NEW.lastUpdated = NOW();
  NEW.fromStoredProcedure = NEW.name + ' says [' + NEW.fromStoredProcedure + '] at ' + NEW.lastUpdated;
END //
DELIMITER ;

