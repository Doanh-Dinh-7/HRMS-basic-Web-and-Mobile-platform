-- 1. Function tạo ID tự động
-- Function tạo DepartmentID (Định dạng: "DEP001", "DEP002", ...)
CREATE OR ALTER FUNCTION dbo.GenerateDepartmentID()
RETURNS NVARCHAR(10)
AS
BEGIN
    DECLARE @MaxID INT;
    SELECT @MaxID = ISNULL(MAX(CAST(SUBSTRING(DepartmentID, 4, LEN(DepartmentID)) AS INT)), 0) 
    FROM Department;

    RETURN 'DEP' + RIGHT('000' + CAST(@MaxID + 1 AS NVARCHAR(3)), 3);
END;
GO

-- Function tạo EmployeeID (Định dạng: "EMP001", "EMP002", ...)
CREATE OR ALTER FUNCTION dbo.GenerateEmployeeID()
RETURNS NVARCHAR(10)
AS
BEGIN
    DECLARE @MaxID INT;
    SELECT @MaxID = ISNULL(MAX(CAST(SUBSTRING(EmployeeID, 4, LEN(EmployeeID)) AS INT)), 0) 
    FROM Employee;

    RETURN 'EMP' + RIGHT('000' + CAST(@MaxID + 1 AS NVARCHAR(3)), 3);
END;
GO

-- Function tạo PayslipID (Định dạng: "PAY001", "PAY002", ...)
CREATE OR ALTER FUNCTION dbo.GeneratePayslipID()
RETURNS NVARCHAR(20)
AS
BEGIN
    DECLARE @MaxID INT;
    SELECT @MaxID = ISNULL(MAX(CAST(SUBSTRING(PayslipID, 4, LEN(PayslipID)) AS INT)), 0) 
    FROM Payslip;

    RETURN 'PAY' + RIGHT('000' + CAST(@MaxID + 1 AS NVARCHAR(3)), 3);
END;
GO

-- 2. Stored Procedure tạo dữ liệu dump
-- Tạo dữ liệu cho bảng Department
CREATE OR ALTER PROCEDURE InsertDepartmentData
AS
BEGIN
    DECLARE @i INT = 1;
    WHILE @i <= 10
    BEGIN
        INSERT INTO Department (DepartmentID, DepartmentName)
        VALUES (dbo.GenerateDepartmentID(), 'Department ' + CAST(@i AS NVARCHAR(2)));

        SET @i = @i + 1;
    END;
END;
GO

-- Thực thi Stored Procedure
EXEC InsertDepartmentData;

-- Tạo dữ liệu cho bảng Employee
CREATE OR ALTER PROCEDURE InsertEmployeeData
AS
BEGIN
    DECLARE @i INT = 1;
    WHILE @i <= 10
    BEGIN
        INSERT INTO Employee (
            EmployeeID, FullName, Position, DepartmentID, Gender, DateOfBirth, Nationality,
            NationalIDNumber, PhoneNumber, HealthInsurance, Address, BankAccountNumber, BankName, Status
        )
        VALUES (
            dbo.GenerateEmployeeID(),
            'Employee ' + CAST(@i AS NVARCHAR(2)),
            CASE WHEN @i % 2 = 0 THEN 'Manager' ELSE 'Staff' END,
            'DEP' + RIGHT('000' + CAST((@i % 5 + 1) AS NVARCHAR(3)), 3),
            CASE WHEN @i % 2 = 0 THEN 'Male' ELSE 'Female' END,
            DATEADD(YEAR, -(@i * 2), GETDATE()),
            'Nationality ' + CAST(@i AS NVARCHAR(2)),
            'NID' + RIGHT('00000' + CAST(@i AS NVARCHAR(5)), 5),
            '0123456789' + CAST(@i AS NVARCHAR(2)),
            'HI' + RIGHT('00000' + CAST(@i AS NVARCHAR(5)), 5),
            'Address ' + CAST(@i AS NVARCHAR(2)),
            'BANKACC' + RIGHT('00000' + CAST(@i AS NVARCHAR(5)), 5),
            'Bank ' + CAST(@i AS NVARCHAR(2)),
            CASE 
                WHEN @i % 3 = 0 THEN 'On leave'
                WHEN @i % 3 = 1 THEN 'Active'
                ELSE 'Inactive'
            END
        );

        SET @i = @i + 1;
    END;
END;
GO

-- Thực thi Stored Procedure
EXEC InsertEmployeeData;

-- Thực thi Stored Procedure
EXEC InsertEmployeeData;

-- Tạo dữ liệu cho bảng TimesheetList
CREATE OR ALTER PROCEDURE InsertTimesheetListData
AS
BEGIN
    DECLARE @i INT = 1;
    WHILE @i <= 10
    BEGIN
        INSERT INTO TimesheetList (
            Name, TimePeriod, Note, Status, LockStatus, EmployeeID
        )
        VALUES (
            'Timesheet ' + CAST(@i AS NVARCHAR(2)),
            DATEADD(DAY, -(@i * 30), GETDATE()),
            'Note for timesheet ' + CAST(@i AS NVARCHAR(2)),
            CASE WHEN @i % 3 = 0 THEN 'To do'
                 WHEN @i % 3 = 1 THEN 'Process'
                 ELSE 'Done' END,
            CASE WHEN @i % 2 = 0 THEN 'Lock' ELSE 'Unlock' END,
            'EMP' + RIGHT('000' + CAST((@i % 5 + 1) AS NVARCHAR(3)), 3)
        );

        SET @i = @i + 1;
    END;
END;
GO

-- Thực thi Stored Procedure
EXEC InsertTimesheetListData;

-- Tạo dữ liệu cho bảng DayTimekeeping
CREATE OR ALTER PROCEDURE InsertDayTimekeepingData
AS
BEGIN
    DECLARE @i INT = 1;
    WHILE @i <= 10
    BEGIN
        INSERT INTO DayTimekeeping (
            TimesheetID, EmployeeID, HoursOfWork
        )
        VALUES (
            @i,
            'EMP' + RIGHT('000' + CAST((@i % 5 + 1) AS NVARCHAR(3)), 3),
            CAST(RAND() * 8 + 1 AS DECIMAL(4,2))
        );

        SET @i = @i + 1;
    END;
END;
GO

-- Thực thi Stored Procedure
EXEC InsertDayTimekeepingData;

-- Tạo dữ liệu cho bảng MobileTimekeeping
CREATE OR ALTER PROCEDURE InsertMobileTimekeepingData
AS
BEGIN
    DECLARE @i INT = 1;
    WHILE @i <= 10
    BEGIN
        INSERT INTO MobileTimekeeping (
            EmployeeID, StatusCheck, DayCheckin, Department, Shift, TimeCheckin, Note, StatusShift, TimeCheckout
        )
        VALUES (
            'EMP' + RIGHT('000' + CAST((@i % 5 + 1) AS NVARCHAR(3)), 3),
            CASE WHEN @i % 2 = 0 THEN 'Check-in' ELSE 'Check-out' END,
            DATEADD(DAY, -(@i * 2), GETDATE()),
            'Department ' + CAST((@i % 5 + 1) AS NVARCHAR(2)),
            CASE WHEN @i % 3 = 0 THEN 'Morning'
                 WHEN @i % 3 = 1 THEN 'Afternoon'
                 ELSE 'Night' END,
            DATEADD(HOUR, -(@i * 2), GETDATE()),
            'Note for check ' + CAST(@i AS NVARCHAR(2)),
            CASE WHEN @i % 2 = 0 THEN 'On shift' ELSE 'End of shift' END,
            CASE WHEN @i % 2 = 0 THEN NULL ELSE DATEADD(HOUR, -(@i * 2) + 8, GETDATE()) END
        );

        SET @i = @i + 1;
    END;
END;
GO

-- Thực thi Stored Procedure
EXEC InsertMobileTimekeepingData;

--  Tạo dữ liệu cho bảng Payslip
CREATE OR ALTER PROCEDURE InsertPayslipData
AS
BEGIN
    DECLARE @i INT = 1;
    WHILE @i <= 10
    BEGIN
        INSERT INTO Payslip (
            PayslipID, EmployeeID, Year, Month, Workday, BasicSalary, NetSalary
        )
        VALUES (
            dbo.GeneratePayslipID(),
            'EMP' + RIGHT('000' + CAST((@i % 5 + 1) AS NVARCHAR(3)), 3),
            YEAR(GETDATE()),
            MONTH(GETDATE()),
            CAST(RAND() * 20 + 1 AS SMALLINT),
            CAST(RAND() * 5000 + 1000 AS DECIMAL(10,2)),
            CAST(RAND() * 5000 + 1000 AS DECIMAL(10,2))
        );

        SET @i = @i + 1;
    END;
END;
GO

-- Thực thi Stored Procedure
EXEC InsertPayslipData;

-- Tạo dữ liệu cho bảng Payslip_Allowence
CREATE OR ALTER PROCEDURE InsertPayslipAllowenceData
AS
BEGIN
    DECLARE @i INT = 1;
    WHILE @i <= 10
    BEGIN
        INSERT INTO Payslip_Allowence (
            PayslipID, AllowenceType, Amount
        )
        VALUES (
            'PAY' + RIGHT('000' + CAST((@i % 5 + 1) AS NVARCHAR(3)), 3),
            'Allowence Type ' + CAST(@i AS NVARCHAR(2)),
            CAST(RAND() * 500 + 100 AS DECIMAL(10,2))
        );

        SET @i = @i + 1;
    END;
END;
GO

-- Thực thi Stored Procedure
EXEC InsertPayslipAllowenceData;

-- Tạo dữ liệu cho bảng Payslip_Deduction
CREATE OR ALTER PROCEDURE InsertPayslipDeductionData
AS
BEGIN
    DECLARE @i INT = 1;
    WHILE @i <= 10
    BEGIN
        INSERT INTO Payslip_Deduction (
            PayslipID, DeductionType, Amount
        )
        VALUES (
            'PAY' + RIGHT('000' + CAST((@i % 5 + 1) AS NVARCHAR(3)), 3),
            CASE WHEN @i % 5 = 0 THEN 'Tax'
                 WHEN @i % 5 = 1 THEN 'Social Insurance'
                 WHEN @i % 5 = 2 THEN 'Health Insurance'
                 WHEN @i % 5 = 3 THEN 'Mistake Deduction'
                 ELSE 'Other' END,
            CAST(RAND() * 300 + 50 AS DECIMAL(10,2))
        );

        SET @i = @i + 1;
    END;
END;
GO

-- Thực thi Stored Procedure
EXEC InsertPayslipDeductionData;

select * from Department
select * from Employee
select * from TimesheetList
select * from DayTimekeeping
select * from MobileTimekeeping
select * from Payslip
select * from Payslip_Allowence
select * from Payslip_Deduction




