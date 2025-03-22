from models.models import get_db_connection  # Import hàm kết nối từ models.py

def get_payslip_list():
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            SELECT PayslipID, e.EmployeeID, FullName, Position, Year, Month, Workday, BasicSalary, NetSalary
            FROM Payslip p JOIN Employee e ON p.EmployeeID = e.EmployeeID;
        """
        cursor.execute(query)
        payslips = [
            {
                "payslipID": row[0],
                "employeeID": row[1],
                "fullName": row[2],
                "position": row[3],
                "year": row[4],
                "month": row[5],
                "workday": row[6],
                "basicSalary": float(row[7]),
                "netSalary": float(row[8])
            }
            for row in cursor.fetchall()
        ]
        return payslips
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()

def get_payslip_detail(payslip_id):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()

        # Lấy thông tin từ bảng Payslip
        query_payslip = """
            SELECT PayslipID, e.EmployeeID, FullName, Position, Year, Month, Workday, BasicSalary, NetSalary
            FROM Payslip p JOIN Employee e ON p.EmployeeID = e.EmployeeID
            WHERE PayslipID = ?;
        """
        cursor.execute(query_payslip, (payslip_id,))
        payslip_row = cursor.fetchone()
        if not payslip_row:
            return None

        payslip_detail = {
            "payslipID": payslip_row[0],
            "employeeID": payslip_row[1],
            "fullName": payslip_row[2],
            "position": payslip_row[3],
            "year": payslip_row[4],
            "month": payslip_row[5],
            "workday": payslip_row[6],
            "basicSalary": float(payslip_row[7]),
            "netSalary": float(payslip_row[8])
        }

        # Lấy thông tin từ bảng Payslip_Allowence
        query_allowance = """
            SELECT AllowenceID, PayslipID, AllowenceType, Amount
            FROM Payslip_Allowence
            WHERE PayslipID = ?;
        """
        cursor.execute(query_allowance, (payslip_id,))
        allowance_records = [
            {
                "allowenceID": row[0],
                "payslipID": row[1],
                "allowenceType": row[2],
                "amount": float(row[3])
            }
            for row in cursor.fetchall()
        ]

        # Lấy thông tin từ bảng Payslip_Deduction
        query_deduction = """
            SELECT DeductionID, PayslipID, DeductionType, Amount
            FROM Payslip_Deduction
            WHERE PayslipID = ?;
        """
        cursor.execute(query_deduction, (payslip_id,))
        deduction_records = [
            {
                "deductionID": row[0],
                "payslipID": row[1],
                "deductionType": row[2],
                "amount": float(row[3])
            }
            for row in cursor.fetchall()
        ]

        payslip_detail["allowances"] = allowance_records
        payslip_detail["deductions"] = deduction_records
        return payslip_detail
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()

def create_payslip(payslip_data):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            INSERT INTO Payslip (
                PayslipID, EmployeeID, Year, Month, Workday, BasicSalary, NetSalary
            ) VALUES ( dbo.GeneratePayslipID(), ?, ?, ?, ?, ?, ?);
        """
        cursor.execute(query, (
            payslip_data['employeeID'],
            payslip_data['year'],
            payslip_data['month'],
            payslip_data.get('workday', 0),
            payslip_data['basicSalary'],
            payslip_data['netSalary']
        ))
        conn.commit()
        return True
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def update_payslip(payslip_id, updated_data):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            UPDATE Payslip
            SET EmployeeID = ?, Year = ?, Month = ?, Workday = ?, 
                BasicSalary = ?, NetSalary = ?
            WHERE PayslipID = ?;
        """
        cursor.execute(query, (
            updated_data.get('employeeID'),
            updated_data.get('year'),
            updated_data.get('month'),
            updated_data.get('workday'),
            updated_data.get('basicSalary'),
            updated_data.get('netSalary'),
            payslip_id
        ))
        conn.commit()
        return True
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def delete_payslip(payslip_id):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = "DELETE FROM Payslip WHERE PayslipID = ?;"
        cursor.execute(query, (payslip_id,))
        conn.commit()
        return True
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()