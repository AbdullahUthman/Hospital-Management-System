CREATE DATABASE hospital;
USE hospital;

CREATE TABLE Patients(
    patientID INT,
    patientName VARCHAR(255),
    age INT, 
    sex VARCHAR(5),
    contactNumber INT,
    address VARCHAR(255),
    fees DOUBLE,
    PRIMARY KEY(patientID),
    CHECK (sex IN ("male", "female"))
);

CREATE TABLE Doctors(
    doctorID INT,
    doctorName VARCHAR(255),
    specialization VARCHAR(255),
    availableFrom TIME,
    availableTo TIME,
    contactNumber INT,
    PRIMARY KEY(doctorID)
);

CREATE TABLE Medicine(
    medicineID INT,
    medicineName VARCHAR(255),
    medicineType VARCHAR(255),
    price DOUBLE,
    qtyAvailable INT,
    isAvailable VARCHAR(100),
    PRIMARY KEY(medicineID),
    CHECK (isAvailable IN ("Out of Stock", "Available"))
);

CREATE TABLE Appointments(
    appointmentID INT,
    patientID INT,
    doctorID INT,
    appointmentDate DATE,
    timeSlot TIME,
    appStatus VARCHAR(255),
    PRIMARY KEY(appointmentID),
    FOREIGN KEY (patientID) REFERENCES Patients(patientID),
    FOREIGN KEY (doctorID) REFERENCES Doctors(doctorID)
);

CREATE TABLE Treatments(
    treatmentID INT,
    appointmentID INT,
    medicineID INT,
    diagnosis VARCHAR(255),
    prescribedMedicine VARCHAR(255),
    notes VARCHAR(255),
    PRIMARY KEY (treatmentID),
    FOREIGN KEY (appointmentID) REFERENCES Appointments(appointmentID),
    FOREIGN KEY (medicineID) REFERENCES Medicine(medicineID)
);

CREATE TABLE Bills(
    billID INT,
    patientID INT,
    appointmentID INT,
    treatmentID INT,
    amount DOUBLE,
    paymentStatus VARCHAR(255),
    dateIssued DATE,
    PRIMARY KEY(billID),
    FOREIGN KEY(patientID) REFERENCES Patients(patientID),
    FOREIGN KEY(appointmentID) REFERENCES Appointments(appointmentID),
    FOREIGN KEY(treatmentID) REFERENCES Treatments(treatmentID),
    CHECK (paymentStatus IN ("Paid", "Unpaid"))
);

DELIMITER //

CREATE PROCEDURE getTotalAmount (IN IdOfTreatment INT)
BEGIN
    SELECT SUM(Doctors.fees + Medicine.price)
    FROM Treatments
    JOIN Medicine ON Medicine.medicineID = Treatments.medicineID
    JOIN Appointments ON Appointments.appointmentID = Treatments.appointmentID
    JOIN Doctors ON Doctors.doctorID = Appointments.doctorID
    WHERE Treatments.treatmentID = IdOfTreatment;
END //

DELIMITER ;
