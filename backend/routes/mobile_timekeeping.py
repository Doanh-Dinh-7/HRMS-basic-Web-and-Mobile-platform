from flask import Blueprint, jsonify, request
from models import (
    get_mobile_timekeeping_by_employee,
    create_mobile_timekeeping,
    update_checkout_and_calculate_hours,
    delete_mobile_timekeeping
)

# Tạo Blueprint cho các API liên quan đến MobileTimekeeping
mobile_timekeeping_bp = Blueprint('mobile_timekeeping', __name__)

# 1. API Lấy danh sách bản ghi theo EmployeeID
@mobile_timekeeping_bp.route('/api/mobile-timekeeping/<string:employee_id>', methods=['GET'])
def get_mobile_timekeeping_api(employee_id):
    try:
        records = get_mobile_timekeeping_by_employee(employee_id)
        if records:
            return jsonify(records), 200
        else:
            return jsonify({"error": "No records found for the given EmployeeID"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 2. API Tạo mới bản ghi trong bảng MobileTimekeeping
@mobile_timekeeping_bp.route('/api/mobile-timekeeping', methods=['POST'])
def create_mobile_timekeeping_api():
    try:
        # Lấy dữ liệu từ request body (JSON)
        timekeeping_data = request.json

        # Kiểm tra các trường bắt buộc
        required_fields = ['employeeID', 'statusCheck', 'dayCheckin', 'department', 'shift', 'timeCheckin', 'statusShift']
        for field in required_fields:
            if field not in timekeeping_data:
                return jsonify({"error": f"Missing required field: {field}"}), 400

        # Gọi hàm tạo bản ghi
        success = create_mobile_timekeeping(timekeeping_data)
        if success:
            return jsonify({"message": "Record created successfully"}), 201
        else:
            return jsonify({"error": "Failed to create record"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 3. API Cập nhật TimeCheckout và tính toán HoursOfWork
@mobile_timekeeping_bp.route('/api/mobile-timekeeping/checkout/<int:check_id>', methods=['PUT'])
def update_checkout_and_calculate_hours_api(check_id):
    try:
        # Lấy dữ liệu từ request body (JSON)
        data = request.json

        # Kiểm tra trường bắt buộc
        if 'timeCheckout' not in data:
            return jsonify({"error": "Missing required field: timeCheckout"}), 400

        # Chuyển đổi timeCheckout thành datetime
        from datetime import datetime
        time_checkout = datetime.strptime(data['timeCheckout'], '%Y-%m-%d %H:%M:%S')

        # Gọi hàm cập nhật
        result, status_code = update_checkout_and_calculate_hours(check_id, time_checkout)
        return jsonify(result), status_code
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 4. API Xóa bản ghi theo CheckID
@mobile_timekeeping_bp.route('/api/mobile-timekeeping/<int:check_id>', methods=['DELETE'])
def delete_mobile_timekeeping_api(check_id):
    try:
        # Gọi hàm xóa bản ghi
        success = delete_mobile_timekeeping(check_id)
        if success:
            return jsonify({"message": "Record deleted successfully"}), 200
        else:
            return jsonify({"error": "Record not found or failed to delete"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500