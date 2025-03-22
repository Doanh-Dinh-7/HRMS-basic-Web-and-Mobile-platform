from models.models import get_db_connection  # Import hàm kết nối từ models.py

def get_department_list():
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute("select DepartmentID, DepartmentName from Department;")
        departments = [
            {"departmentID": row[0], "departmentName": row[1]}
            for row in cursor.fetchall()
        ]
        return departments
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
