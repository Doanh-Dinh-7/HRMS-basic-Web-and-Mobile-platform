from models.models import get_db_connection  # Import hàm kết nối từ models.py

def get_employee_list():
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute("select EmployeeID, FullName, PhoneNumber, Position, DepartmentName, Status from Employee as e join Department as d on d.DepartmentID = e.DepartmentID;")
        employees = [
            {"employeeID": row[0], "fullName": row[1], "phoneNumber": row[2], "position":row[3], "departmentName": row[4], "status": row[5]}
            for row in cursor.fetchall()
        ]
        return employees
    except Exception as e:
        conn.rollback() # Rollback trong trường hợp có lỗi
        raise e # Ném lỗi ra cho API xư lý
    finally:
        cursor.close()
        conn.close()
        
def get_employee_detail(employee_id):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            SELECT e.EmployeeID, e.FullName, e.Position, e.DepartmentID, d.DepartmentName, 
                   e.Gender, e.DateOfBirth, e.Nationality, e.NationalIDNumber, 
                   e.PhoneNumber, e.HealthInsurance, e.Address, e.BankAccountNumber, 
                   e.BankName, e.Status
            FROM Employee AS e
            JOIN Department AS d ON e.DepartmentID = d.DepartmentID
            WHERE e.EmployeeID = ?;
        """
        cursor.execute(query, (employee_id,))
        row = cursor.fetchone()
        if row:
            employee_detail = {
                "employeeID": row[0],
                "fullName": row[1],
                "position": row[2],
                "departmentID": row[3],
                "departmentName": row[4],
                "gender": row[5],
                "dateOfBirth": row[6],
                "nationality": row[7],
                "nationalIDNumber": row[8],
                "phoneNumber": row[9],
                "healthInsurance": row[10],
                "address": row[11],
                "bankAccountNumber": row[12],
                "bankName": row[13],
                "status": row[14]
            }
            return employee_detail
        else:
            return None  # Trả về None nếu không tìm thấy nhân viên
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def create_employee(employee_data):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            INSERT INTO Employee (
                EmployeeID, FullName, Position, DepartmentID, Gender, DateOfBirth, 
                Nationality, NationalIDNumber, PhoneNumber, HealthInsurance, Address, 
                BankAccountNumber, BankName, Status
            ) VALUES (dbo.GenerateEmployeeID(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """
        cursor.execute(query, (
            employee_data['fullName'],
            employee_data['position'],
            employee_data['departmentID'],
            employee_data['gender'],
            employee_data['dateOfBirth'],
            employee_data['nationality'],
            employee_data['nationalIDNumber'],
            employee_data['phoneNumber'],
            employee_data.get('healthInsurance', None),
            employee_data['address'],
            employee_data['bankAccountNumber'],
            employee_data['bankName'],
            employee_data.get('status', 'Active')  # Mặc định là 'Active'
        ))
        conn.commit()
        return True  # Trả về True nếu thành công
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def update_employee(employee_id, updated_data):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            UPDATE Employee
            SET FullName = ?, Position = ?, DepartmentID = ?, Gender = ?, 
                DateOfBirth = ?, Nationality = ?, NationalIDNumber = ?, 
                PhoneNumber = ?, HealthInsurance = ?, Address = ?, 
                BankAccountNumber = ?, BankName = ?, Status = ?
            WHERE EmployeeID = ?;
        """
        cursor.execute(query, (
            updated_data.get('fullName'),
            updated_data.get('position'),
            updated_data.get('departmentID'),
            updated_data.get('gender'),
            updated_data.get('dateOfBirth'),
            updated_data.get('nationality'),
            updated_data.get('nationalIDNumber'),
            updated_data.get('phoneNumber'),
            updated_data.get('healthInsurance'),
            updated_data.get('address'),
            updated_data.get('bankAccountNumber'),
            updated_data.get('bankName'),
            updated_data.get('status'),
            employee_id
        ))
        conn.commit()
        return True  # Trả về True nếu thành công
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def delete_employee(employee_id):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = "DELETE FROM Employee WHERE EmployeeID = ?;"
        cursor.execute(query, (employee_id,))
        conn.commit()
        return True  # Trả về True nếu thành công
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()


        
if __name__ == '__main__':
    employees = get_employee_list()
    print(employees)
