from flask import Blueprint, jsonify, request
from models import (
    get_timesheet_list,
    get_timesheet_detail,
    create_timesheet,
    update_timesheet,
    delete_timesheet
)

# Tạo Blueprint cho các API liên quan đến Timesheet và DayTimekeeping
timekeeping_bp = Blueprint('timekeeping', __name__)

# 1. API Lấy danh sách TimesheetList
@timekeeping_bp.route('/api/timesheets', methods=['GET'])
def get_timesheet_list_api():
    try:
        timesheets = get_timesheet_list()
        return jsonify(timesheets), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 2. API Xem chi tiết TimesheetList (bao gồm DayTimekeeping)
@timekeeping_bp.route('/api/timesheets/<int:timesheet_id>', methods=['GET'])
def get_timesheet_detail_api(timesheet_id):
    try:
        timesheet_detail = get_timesheet_detail(timesheet_id)
        if timesheet_detail:
            return jsonify(timesheet_detail), 200
        else:
            return jsonify({"error": "Timesheet not found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 3. API Tạo mới TimesheetList
@timekeeping_bp.route('/api/timesheets', methods=['POST'])
def create_timesheet_api():
    try:
        # Lấy dữ liệu từ request body (JSON)
        timesheet_data = request.json

        # Kiểm tra các trường bắt buộc
        required_fields = ['name', 'timePeriod', 'employeeID']
        for field in required_fields:
            if field not in timesheet_data:
                return jsonify({"error": f"Missing required field: {field}"}), 400

        # Gọi hàm tạo Timesheet
        success = create_timesheet(timesheet_data)
        if success:
            return jsonify({"message": "Timesheet created successfully"}), 201
        else:
            return jsonify({"error": "Failed to create timesheet"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 4. API Cập nhật TimesheetList
@timekeeping_bp.route('/api/timesheets/<int:timesheet_id>', methods=['PUT'])
def update_timesheet_api(timesheet_id):
    try:
        # Lấy dữ liệu từ request body (JSON)
        updated_data = request.json

        # Kiểm tra xem có dữ liệu cần cập nhật hay không
        if not updated_data:
            return jsonify({"error": "No data provided for update"}), 400

        # Gọi hàm cập nhật Timesheet
        success = update_timesheet(timesheet_id, updated_data)
        if success:
            return jsonify({"message": "Timesheet updated successfully"}), 200
        else:
            return jsonify({"error": "Failed to update timesheet"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 5. API Xóa TimesheetList
@timekeeping_bp.route('/api/timesheets/<int:timesheet_id>', methods=['DELETE'])
def delete_timesheet_api(timesheet_id):
    try:
        # Gọi hàm xóa Timesheet
        success = delete_timesheet(timesheet_id)
        if success:
            return jsonify({"message": "Timesheet deleted successfully"}), 200
        else:
            return jsonify({"error": "Timesheet not found or failed to delete"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500