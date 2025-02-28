from models.models import get_db_connection  # Import hàm kết nối từ models.py

def get_all_sach():
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute("select maSach, tenSach, theLoai, namXuatBan, tenTacGia from Sach join TacGia on Sach.maTacGia = TacGia.maTacGia;")
        sach = [
            {"maSach": row[0], "tenSach": row[1], "theLoai": row[2], "namXuatBan":row[3], "tenTacGia": row[4]}
            for row in cursor.fetchall()
        ]
        return sach
    except Exception as e:
        conn.rollback() # Rollback trong trường hợp có lỗi
        raise e # Ném lỗi ra cho API xư lý
    finally:
        cursor.close()
        conn.close()

def search_TenSach(tenSach):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute("select maSach, tenSach, theLoai, namXuatBan, tenTacGia from Sach join TacGia on Sach.maTacGia = TacGia.maTacGia where tenSach like ('%'+ ? +'%');", 
                       (tenSach))
        sach = [
            {"maSach": row[0], "tenSach": row[1], "theLoai": row[2], "namXuatBan":row[3], "tenTacGia": row[4]}
            for row in cursor.fetchall()
        ]
        return sach
    except Exception as e:
        conn.rollback() # Rollback trong trường hợp có lỗi
        raise e # Ném lỗi ra cho API xư lý
    finally:
        cursor.close()
        conn.close()

def search_MaTacGia(maTacGia):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute("select maSach from Sach where maTacGia = ? ;", 
                       (maTacGia))
        sach = [
            {"maSach": row[0]}
            for row in cursor.fetchall()
        ]
        return sach
    except Exception as e:
        conn.rollback() # Rollback trong trường hợp có lỗi
        raise e # Ném lỗi ra cho API xư lý
    finally:
        cursor.close()
        conn.close()

def search_TheLoai(theLoai):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute("select maSach from Sach where theLoai = ?;", 
                       (theLoai))
        sach = [
            {"maSach": row[0]}
            for row in cursor.fetchall()
        ]
        return sach
    except Exception as e:
        conn.rollback() # Rollback trong trường hợp có lỗi
        raise e # Ném lỗi ra cho API xư lý
    finally:
        cursor.close()
        conn.close()
        
if __name__ == '__main__':
    sach = search_TheLoai('vanhoc')
    print(sach)
