
INSERT INTO patient
    (name, birth_date, email, gender, blood_group, created_at)
VALUES
    ('Rahul Sharma', '1998-05-15', 'rahul.sharma@example.com', 'Male', 'O_POSITIVE', CURRENT_TIMESTAMP),
    ('Priya Singh', '2001-08-22', 'priya.singh@example.com', 'Female', 'A_POSITIVE', CURRENT_TIMESTAMP),
    ('Amit Verma', '1995-11-10', 'amit.verma@example.com', 'Male', 'B_POSITIVE', CURRENT_TIMESTAMP),
    ('Sneha Gupta', '1999-03-25', 'sneha.gupta@example.com', 'Female', 'AB_POSITIVE', CURRENT_TIMESTAMP),
    ('Rohit Kumar', '1997-12-05', 'rohit.kumar@example.com', 'Male', 'A_NEGATIVE', CURRENT_TIMESTAMP);





INSERT INTO doctor (name, specialization, email)
VALUES
('Dr. Rakesh Mehta', 'Cardiology', 'rakesh.mehta@example.com'),
('Dr. Sneha Kapoor', 'Dermatology', 'sneha.kapoor@example.com'),
('Dr. Arjun Nair', 'Orthopedics', 'arjun.nair@example.com');