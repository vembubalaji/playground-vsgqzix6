CREATE TABLE User(user_Id integer PRIMARY KEY,Name varchar(255),Username varchar(255),IsAdmin boolean,Region_Id integer NOT NULL,team_id integer NOT NULL,domain_id integer NOT NULL,tech_id integer NOT NULL);
CREATE TABLE Domain( domain_Id integer PRIMARY KEY, Name varchar(255) NOT NULL);
CREATE TABLE Region(region_Id integer PRIMARY KEY,Name varchar(255) NOT NULL);
CREATE TABLE Team(team_Id integer PRIMARY KEY,Name varchar(255) NOT NULL,Region_Id integer NOT NULL, domain_id integer NOT NULL);
CREATE TABLE TECHNOLOGY(tech_id integer PRIMARY KEY, name varchar(255) not NULL);
	
ALTER TABLE User ADD  CONSTRAINT FK_User_Region FOREIGN KEY(Region_Id) REFERENCES Region;
ALTER TABLE User ADD  CONSTRAINT FK_User_team FOREIGN KEY(team_id) REFERENCES Team;
ALTER TABLE User ADD  CONSTRAINT FK_User_domain FOREIGN KEY(domain_id) REFERENCES Domain;
ALTER TABLE User ADD  CONSTRAINT FK_User_tech FOREIGN KEY(tech_id) REFERENCES TECHNOLOGY;
ALTER TABLE Team  ADD CONSTRAINT FK_Team_Region FOREIGN KEY(Region_Id) REFERENCES Region;
ALTER TABLE Team  ADD CONSTRAINT FK_Team_domain FOREIGN KEY(domain_Id) REFERENCES domain;

INSERT INTO region (region_id,name) VALUES (1,'United States');
INSERT INTO region (region_id,name) VALUES (2,'Canada');


INSERT INTO domain (domain_id, name) values (1, 'Doamin1');
INSERT INTO domain (domain_id, name) values (2, 'Domain2');

INSERT INTO team (team_Id,name,region_id,domain_id) VALUES (1,'Team1',1,1);
INSERT INTO team (team_Id,name,region_id,domain_id) VALUES (2,'Team2',1,2);

INSERT INTO TECHNOLOGY (TECH_ID, NAME) VALUES (1, 'JAVA');
INSERT INTO TECHNOLOGY (TECH_ID, NAME) VALUES (2, '.Net');

INSERT INTO user (user_Id,name,username,isadmin,region_id,team_id,domain_id,tech_id) VALUES (1,'FirstName1 LastName1','User1',true,1,1,1,1);
INSERT INTO user (user_Id,name,username,isadmin,region_id,team_id,domain_id,tech_id) VALUES (2,'FirstName2 LastName2','User2',true,2,2,2,2);
INSERT INTO user (user_Id,name,username,isadmin,region_id,team_id,domain_id,tech_id) VALUES (3,'abc def','aaa',true,2,2,2,2);