INSERT INTO patient100 (name, email, birth_date, gender, blood_group)
VALUES
('Rahul Kumar', 'rahul@example.com', '1990-05-15', 'MALE', 'O_positive'),
('Priya Sharma', 'priya@example.com', '1992-08-20', 'FEMALE', 'A_positive'),
('Amit Patel', 'amit@example.com', '1988-03-10', 'MALE', 'B_positive');
INSERT INTO doctor (name, specialization, email)
VALUES
('Dr. Rakesh Mehta', 'Cardiology', 'rakesh.mehta@example.com'),
('Dr. Sneha Kapoor', 'Dermatology', 'sneha.kapoor@example.com'),
('Dr. Arjun Nair', 'Orthopedics', 'arjun.nair@example.com');

INSERT INTO appointment (appointment_time, reason, doctor_id, patient_id)
VALUES
('2025-07-01 10:30:00', 'General Checkup', 1, 2),
('2025-07-02 11:00:00', 'Skin Rash', 2, 2),
('2025-07-03 09:45:00', 'Knee Pain', 3, 3),
('2025-07-04 14:00:00', 'Follow-up Visit', 1, 1);


