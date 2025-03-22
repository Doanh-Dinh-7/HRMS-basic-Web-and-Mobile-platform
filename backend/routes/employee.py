from flask import Blueprint, jsonify, request
from models import get_employee_list, get_employee_detail, create_employee, update_employee, delete_employee

# Tạo Blueprint cho các API liên quan đến nhân viên
employee_bp = Blueprint('employee', __name__)

# API Lấy danh sách nhân viên
@employee_bp.route('/api/employees', methods=['GET'])
def get_employee_list_api():
    try:
        all_employees = get_employee_list()
        return jsonify(all_employees), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# API Xem thông tin chi tiết của một nhân viên
@employee_bp.route('/api/employees/<string:employee_id>', methods=['GET'])
def get_employee_detail_api(employee_id):
    try:
        employee_detail = get_employee_detail(employee_id)
        if employee_detail:
            return jsonify(employee_detail), 200
        else:
            return jsonify({"error": "Employee not found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# API Tạo mới nhân viên
@employee_bp.route('/api/employees', methods=['POST'])
def create_employee_api():
    try:
        employee_data = request.json

        required_fields = ['fullName', 'position', 'departmentID', 'gender', 
                           'dateOfBirth', 'nationality', 'nationalIDNumber', 'phoneNumber', 
                           'address', 'bankAccountNumber', 'bankName']
        for field in required_fields:
            if field not in employee_data:
                return jsonify({"error": f"Missing required field: {field}"}), 400

        success = create_employee(employee_data)
        if success:
            return jsonify({"message": "Employee created successfully"}), 201
        else:
            return jsonify({"error": "Failed to create employee"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# API Cập nhật thông tin nhân viên
@employee_bp.route('/api/employees/<string:employee_id>', methods=['PUT'])
def update_employee_api(employee_id):
    try:
        updated_data = request.json

        if not updated_data:
            return jsonify({"error": "No data provided for update"}), 400

        success = update_employee(employee_id, updated_data)
        if success:
            return jsonify({"message": "Employee updated successfully"}), 200
        else:
            return jsonify({"error": "Failed to update employee"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# API Xoá nhân viên
@employee_bp.route('/api/employees/<string:employee_id>', methods=['DELETE'])
def delete_employee_api(employee_id):
    try:
        success = delete_employee(employee_id)
        if success:
            return jsonify({"message": "Employee deleted successfully"}), 200
        else:
            return jsonify({"error": "Employee not found or failed to delete"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500