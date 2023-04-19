CREATE SCHEMA `doctors_office`;

CREATE TABLE doctors_office.Patients(
    patient_id int,
    patient_name varchar(255),
    date_of_birth varchar(255),
    PRIMARY KEY (patient_id)
);

CREATE TABLE doctors_office.Doctors(
    doctor_id int,
    doctor_name varchar(255),
    focus varchar(255),
    PRIMARY KEY (doctor_id)
);

CREATE TABLE doctors_office.Appointments(
    appointment_id int,
    appointment_time varchar(255),
    patient_id int,
    doctor_id int,
    PRIMARY KEY (appointment_id),
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id)
);