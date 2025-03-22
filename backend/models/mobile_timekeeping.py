from models.models import get_db_connection  # Import hàm kết nối từ models.py

def get_mobile_timekeeping_by_employee(employee_id):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            SELECT CheckID, EmployeeID, StatusCheck, DayCheckin, Department, Shift, 
                   TimeCheckin, Note, StatusShift, TimeCheckout
            FROM MobileTimekeeping
            WHERE EmployeeID = ?;
        """
        cursor.execute(query, (employee_id,))
        records = [
            {
                "checkID": row[0],
                "employeeID": row[1],
                "statusCheck": row[2],
                "dayCheckin": row[3],
                "department": row[4],
                "shift": row[5],
                "timeCheckin": row[6],
                "note": row[7],
                "statusShift": row[8],
                "timeCheckout": row[9]
            }
            for row in cursor.fetchall()
        ]
        return records
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()
        
def create_mobile_timekeeping(timekeeping_data):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = """
            INSERT INTO MobileTimekeeping (
                EmployeeID, StatusCheck, DayCheckin, Department, Shift, TimeCheckin, 
                Note, StatusShift, TimeCheckout
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
        """
        cursor.execute(query, (
            timekeeping_data['employeeID'],
            timekeeping_data['statusCheck'],
            timekeeping_data['dayCheckin'],
            timekeeping_data['department'],
            timekeeping_data['shift'],
            timekeeping_data['timeCheckin'],
            timekeeping_data.get('note', None),
            timekeeping_data['statusShift'],
            timekeeping_data.get('timeCheckout', None)
        ))
        conn.commit()
        return True
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()

def update_checkout_and_calculate_hours(check_id, time_checkout):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()

        # Bước 1: Lấy thông tin từ bảng MobileTimekeeping
        query_get_mobile_timekeeping = """
            SELECT EmployeeID, DayCheckin, TimeCheckin
            FROM MobileTimekeeping
            WHERE CheckID = ?;
        """
        cursor.execute(query_get_mobile_timekeeping, (check_id,))
        mobile_row = cursor.fetchone()
        if not mobile_row:
            return {"error": "CheckID not found"}, 404

        employee_id, day_checkin, time_checkin = mobile_row

        # Bước 2: Tính toán số giờ làm việc
        if time_checkin and time_checkout:
            time_diff = (time_checkout - time_checkin).total_seconds() / 3600  # Chuyển sang giờ
            hours_of_work = round(time_diff, 2)  # Làm tròn lấy giờ
        else:
            return {"error": "Invalid TimeCheckin or TimeCheckout"}, 400

        # Bước 3: Tìm TimesheetID phù hợp với điều kiện
        query_get_timesheet_id = """
            SELECT TimesheetID
            FROM TimesheetList
            WHERE MONTH(TimePeriod) = MONTH(?) AND YEAR(TimePeriod) = YEAR(?);
        """
        cursor.execute(query_get_timesheet_id, (day_checkin, day_checkin))
        timesheet_row = cursor.fetchone()
        if not timesheet_row:
            return {"error": "No matching Timesheet for the given month and year"}, 404

        timesheet_id = timesheet_row[0]

        # Bước 4: Cập nhật HoursOfWork trong bảng DayTimekeeping
        query_update_hours_of_work = """
            UPDATE DayTimekeeping
            SET HoursOfWork = ISNULL(HoursOfWork, 0) + ?
            WHERE TimesheetID = ? AND EmployeeID = ?;
        """
        cursor.execute(query_update_hours_of_work, (hours_of_work, timesheet_id, employee_id))

        # Bước 5: Cập nhật TimeCheckout trong bảng MobileTimekeeping
        query_update_checkout = """
            UPDATE MobileTimekeeping
            SET TimeCheckout = ?
            WHERE CheckID = ?;
        """
        cursor.execute(query_update_checkout, (time_checkout, check_id))

        conn.commit()
        return {"message": "Checkout updated and hours calculated successfully"}, 200

    except Exception as e:
        conn.rollback()
        return {"error": str(e)}, 500
    finally:
        cursor.close()
        conn.close()
        
def delete_mobile_timekeeping(check_id):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        query = "DELETE FROM MobileTimekeeping WHERE CheckID = ?;"
        cursor.execute(query, (check_id,))
        conn.commit()
        return True
    except Exception as e:
        conn.rollback()
        raise e
    finally:
        cursor.close()
        conn.close()