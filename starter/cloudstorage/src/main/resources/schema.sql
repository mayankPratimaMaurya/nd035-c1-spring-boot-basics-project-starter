-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteid INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    filedata BLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);

INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES('q', 'IY7aZsCdBDZApFNc3tlUyw==', 'zT/LmvIdvTHsKGp80nxdcA==', 'q', 'q');
-- INSERT INTO NOTES(notetitle,notedescription,userid) VALUES('titel1','descrption1',1);
-- INSERT INTO NOTES(notetitle,notedescription,userid) VALUES('titel2','descrption2',1);
-- INSERT INTO NOTES(notetitle,notedescription,userid) VALUES('titel3','descrption3',1);
-- INSERT INTO NOTES(notetitle,notedescription,userid) VALUES('title4','descrption4',1);

-- INSERT INTO CREDENTIALS(url,username,key,password,userid) VALUES('https://google.com','usernae1','key1','passsword1',1);
-- INSERT INTO CREDENTIALS(url,username,key,password,userid) VALUES('details2','usernae2','key2','passsword2',1);
-- INSERT INTO CREDENTIALS(url,username,key,password,userid) VALUES('details3','usernae3','key3','passsword3',1);
-- INSERT INTO CREDENTIALS(url,username,key,password,userid) VALUES('details4','usernae4','key4','passsword4',1);