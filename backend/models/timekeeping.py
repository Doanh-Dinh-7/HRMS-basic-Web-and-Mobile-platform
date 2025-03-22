from models.models import get_db_connection  # Import hàm kết nối từ models.py

def get_timesheet_list():
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            SELECT TimesheetID, Name, TimePeriod, Note, Status, LockStatus, EmployeeID
            FROM TimesheetList;
        """
        cursor.execute(query)
        timesheets = [
            {
                "timesheetID": row[0],
                "name": row[1],
                "timePeriod": row[2],
                "note": row[3],
                "status": row[4],
                "lockStatus": row[5],
                "employeeID": row[6]
            }
            for row in cursor.fetchall()
        ]
        return timesheets
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def get_timesheet_detail(timesheet_id):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()

        # Lấy thông tin từ bảng TimesheetList
        query_timesheet = """
            SELECT TimesheetID, Name, TimePeriod, Note, Status, LockStatus, EmployeeID
            FROM TimesheetList
            WHERE TimesheetID = ?;
        """
        cursor.execute(query_timesheet, (timesheet_id,))
        timesheet_row = cursor.fetchone()
        if not timesheet_row:
            return None

        timesheet_detail = {
            "timesheetID": timesheet_row[0],
            "name": timesheet_row[1],
            "timePeriod": timesheet_row[2],
            "note": timesheet_row[3],
            "status": timesheet_row[4],
            "lockStatus": timesheet_row[5],
            "employeeID": timesheet_row[6]
        }

        # Lấy thông tin từ bảng DayTimekeeping
        query_daytimekeeping = """
            SELECT RecordID, TimesheetID, d.EmployeeID, e.FullName, HoursOfWork
            FROM DayTimekeeping d JOIN Employee e on d.EmployeeID = e.EmployeeID
            WHERE TimesheetID = ?;
        """
        cursor.execute(query_daytimekeeping, (timesheet_id,))
        daytimekeeping_records = [
            {
                "recordID": row[0],
                "timesheetID": row[1],
                "employeeID": row[2],
                "fullName": row[3],
                "hoursOfWork": float(row[4]) if row[4] else 0.0
            }
            for row in cursor.fetchall()
        ]

        timesheet_detail["dayTimekeeping"] = daytimekeeping_records
        return timesheet_detail
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def create_timesheet(timesheet_data):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            INSERT INTO TimesheetList (
                Name, TimePeriod, Note, Status, LockStatus, EmployeeID
            ) VALUES (?, ?, ?, ?, ?, ?);
        """
        cursor.execute(query, (
            timesheet_data['name'],
            timesheet_data['timePeriod'],
            timesheet_data.get('note', None),
            timesheet_data.get('status', 'To do'),
            timesheet_data.get('lockStatus', 'Unlock'),
            timesheet_data['employeeID']
        ))
        conn.commit()
        return True
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def update_timesheet(timesheet_id, updated_data):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            UPDATE TimesheetList
            SET Name = ?, TimePeriod = ?, Note = ?, Status = ?, LockStatus = ?, EmployeeID = ?
            WHERE TimesheetID = ?;
        """
        cursor.execute(query, (
            updated_data.get('name'),
            updated_data.get('timePeriod'),
            updated_data.get('note'),
            updated_data.get('status'),
            updated_data.get('lockStatus'),
            updated_data.get('employeeID'),
            timesheet_id
        ))
        conn.commit()
        return True
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def delete_timesheet(timesheet_id):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = "DELETE FROM TimesheetList WHERE TimesheetID = ?;"
        cursor.execute(query, (timesheet_id,))
        conn.commit()
        return True
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()    