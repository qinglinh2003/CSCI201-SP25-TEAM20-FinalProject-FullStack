CREATE DATABASE FinalProject;
DROP TABLE IF EXISTS UserCourse;
DROP TABLE IF EXISTS Assignment;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS users;

 CREATE TABLE users
 (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    university VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Course 
(
	courseID INT PRIMARY KEY AUTO_INCREMENT,
	courseName TEXT,
    courseDates TEXT,
    courseTime TEXT
); 

CREATE TABLE UserCourse 
(
	user_id INT,
    courseID INT, -- matches Course table's primary key
    PRIMARY KEY (user_id, courseID),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE, -- deleting from the reference table also deletes this --> cascading effect 
    FOREIGN KEY (courseID) REFERENCES course(courseID) ON DELETE CASCADE
);

CREATE TABLE Assignment
(
	assignmentID INT PRIMARY KEY AUTO_INCREMENT,
	courseID INT NOT NULL,
    aName VARCHAR(100),
    dueDate VARCHAR(45) NOT NULL,
    dueTime VARCHAR(45) NOT NULL,
    descr TEXT,
    FOREIGN KEY (courseID) REFERENCES COURSE(courseID) ON DELETE CASCADE
);

-- INSERT SAMPLE USERS
INSERT INTO users (full_name, university, email, password_)
VALUES 
('Alice Smith', 'UCLA', 'alice@ucla.edu', 'hashed_pw1'),
('Bob Lee', 'USC', 'bob@usc.edu', 'hashed_pw2');

-- INSERT SAMPLE COURSES
INSERT INTO Course (courseName, courseDates, courseTime)
VALUES
('CSCI201', 'Monday', '10:00'),
('CSCI310', 'Wednesday', '14:00');

-- INSERT SAMPLE ASSIGNMENTS
INSERT INTO Assignment (courseID, aName, dueDate, dueTime, descr)
VALUES
(1, 'HW1', '2025-05-10', '23:59', 'Intro to Java Servlets'),
(1, 'HW2', '2025-05-20', '23:59', 'JDBC and SQL integration'),
(2, 'Project Proposal', '2025-05-15', '17:00', 'Propose final team project');

-- ENROLL USERS IN COURSES
INSERT INTO UserCourse (user_id, courseID)
VALUES
(1, 1),
(1, 2),
(2, 1);

-- CREATE TABLE UserAssignment
-- (
-- 	user_id INT,
--   courseID INT NOT NULL,
--   dueDate VARCHAR(45) NOT NULL,
-- dueTime VARCHAR(45) NOT NULL,
--  descr VARCHAR(200) NOT NULL, PRIMARY KEY (userEmail, assignmentID),
-- FOREIGN KEY (userEmail) REFERENCES User(userEmail), 
-- FOREIGN KEY (assignmentID) REFERENCES Assignment(assignmentID),
-- FOREIGN KEY (courseID) REFERENCES COURSE(courseID)
-- );

