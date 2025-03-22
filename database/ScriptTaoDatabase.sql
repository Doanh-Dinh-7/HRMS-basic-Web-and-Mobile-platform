-- Tạo cơ sở dữ liệu HRMS
CREATE DATABASE HRMS;
GO

USE HRMS;
GO

-- 1. Bảng Department
CREATE TABLE Department (
    DepartmentID NVARCHAR(10) PRIMARY KEY, 
    DepartmentName NVARCHAR(100) NOT NULL 
);
GO

-- 2. Bảng Employee
CREATE TABLE Employee (
    EmployeeID NVARCHAR(10) PRIMARY KEY, 
    FullName NVARCHAR(100) NOT NULL,
    Position NVARCHAR(50) NOT NULL,
    DepartmentID NVARCHAR(10) NOT NULL, 
    Gender NVARCHAR(10) NOT NULL CHECK (Gender IN ('Male', 'Female')), 
    DateOfBirth DATE NOT NULL,
    Nationality NVARCHAR(50) NOT NULL,
    NationalIDNumber NVARCHAR(15) NOT NULL,
    PhoneNumber NVARCHAR(15) NOT NULL, 
    HealthInsurance NVARCHAR(15), 
    Address NVARCHAR(255) NOT NULL, 
    BankAccountNumber NVARCHAR(20) NOT NULL, 
    BankName NVARCHAR(100) NOT NULL,
	Status NVARCHAR(10) NOT NULL DEFAULT 'Active' -- Giá trị mặc định là 'Active'
	CHECK (Status IN ('On leave', 'Active', 'Inactive')),
    FOREIGN KEY (DepartmentID) REFERENCES Department(DepartmentID)
);
GO

-- 3. Bảng TimesheetList
CREATE TABLE TimesheetList (
    TimesheetID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    TimePeriod DATE NOT NULL,
    Note NVARCHAR(255),
    Status NVARCHAR(10) NOT NULL CHECK (Status IN ('To do', 'Process', 'Done')),
    LockStatus NVARCHAR(10) NOT NULL CHECK (LockStatus IN ('Lock', 'Unlock')),
    EmployeeID NVARCHAR(10), 
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);
GO

-- 4. Bảng DayTimekeeping
CREATE TABLE DayTimekeeping (
    RecordID INT IDENTITY(1,1) PRIMARY KEY,
    TimesheetID INT NOT NULL, 
    EmployeeID NVARCHAR(10) NOT NULL, 
    HoursOfWork DECIMAL(4,2) CHECK (HoursOfWork >= 0), 
    FOREIGN KEY (TimesheetID) REFERENCES TimesheetList(TimesheetID),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);
GO

-- 5. Bảng MobileTimekeeping
CREATE TABLE MobileTimekeeping (
    CheckID INT IDENTITY(1,1) PRIMARY KEY,
    EmployeeID NVARCHAR(10) NOT NULL, 
    StatusCheck NVARCHAR(10) NOT NULL CHECK (StatusCheck IN ('Check-in', 'Check-out')),
    DayCheckin DATE NOT NULL,
    Department NVARCHAR(100) NOT NULL,
    Shift NVARCHAR(10) NOT NULL CHECK (Shift IN ('Morning', 'Afternoon', 'Night')),
    TimeCheckin DATETIME NOT NULL,
    Note NVARCHAR(255),
    StatusShift NVARCHAR(20) NOT NULL CHECK (StatusShift IN ('On shift', 'End of shift')),
    TimeCheckout DATETIME,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);
GO

-- 6. Bảng Payslip
CREATE TABLE Payslip (
    PayslipID NVARCHAR(20) PRIMARY KEY, 
    EmployeeID NVARCHAR(10) NOT NULL, 
    Year SMALLINT NOT NULL CHECK (Year > 0), 
    Month TINYINT NOT NULL CHECK (Month BETWEEN 1 AND 12),
    Workday SMALLINT CHECK (Workday >= 0), 
    BasicSalary DECIMAL(10,2) NOT NULL, 
    NetSalary DECIMAL(10,2) NOT NULL, 
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);
GO

-- 7. Bảng Payslip_Allowence
CREATE TABLE Payslip_Allowence (
    AllowenceID INT IDENTITY(1,1) PRIMARY KEY,
    PayslipID NVARCHAR(20) NOT NULL, 
    AllowenceType NVARCHAR(100) NOT NULL,
    Amount DECIMAL(10,2) NOT NULL, 
    FOREIGN KEY (PayslipID) REFERENCES Payslip(PayslipID)
);
GO

-- 8. Bảng Payslip_Deduction
CREATE TABLE Payslip_Deduction (
    DeductionID INT IDENTITY(1,1) PRIMARY KEY,
    PayslipID NVARCHAR(20) NOT NULL, 
    DeductionType NVARCHAR(50) NOT NULL CHECK (DeductionType IN ('Tax', 'Social Insurance', 'Health Insurance', 'Mistake Deduction', 'Other')),
    Amount DECIMAL(10,2) NOT NULL, 
    FOREIGN KEY (PayslipID) REFERENCES Payslip(PayslipID)
);
GO