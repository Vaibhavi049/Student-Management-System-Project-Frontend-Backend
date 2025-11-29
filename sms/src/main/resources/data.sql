-- Seed test data
INSERT INTO user_info (id, email, password, type, name, mobile_number, department) 
VALUES 
('1', 'admin@sms.com', '$2a$10$WKVbR.OmVPF6jH/jMzqvJOKJGBPh5EI3POyqEMtAaAMDqNFEV6UYK', 'ADMIN', 'Admin User', '9999999999', 'IT'),
('2', 'teacher@sms.com', '$2a$10$4RBR.6Bj0fFQbJHB5OqSbOcgB1mBt5C6mJkF5Z.kPmq9qN2s5RqDe', 'TEACHER', 'Teacher User', '8888888888', 'Science'),
('3', 'student@sms.com', '$2a$10$X7KL.9Pq2uH3vM5nP8rSYOOcG1jB5K8lQ2bX4cY6zZ1a9w3s5T9Ke', 'STUDENT', 'Student User', '7777777777', 'IT');

INSERT INTO course (id, course_code, course_name, description, credits, instructor_name) 
VALUES 
(1, 'CS101', 'Introduction to Computer Science', 'Basics of programming', 4, 'Dr. Smith'),
(2, 'CS201', 'Data Structures', 'Learn fundamental data structures', 4, 'Dr. Johnson'),
(3, 'CS301', 'Algorithms', 'Advanced algorithms and complexity', 3, 'Dr. Williams');

INSERT INTO enrollment (id, user_id, course_id, enrollment_date, grade) 
VALUES 
(1, 3, 1, NOW(), 'A'),
(2, 3, 2, NOW(), 'B+'),
(3, 2, 1, NOW(), NULL);
