from flask import Blueprint, jsonify, request
from models import (
    get_payslip_list,
    get_payslip_detail,
    create_payslip,
    update_payslip,
    delete_payslip
)

# Tạo Blueprint cho các API liên quan đến Payslip
payslip_bp = Blueprint('payslip', __name__)

# 1. API Lấy danh sách Payslip
@payslip_bp.route('/api/payslips', methods=['GET'])
def get_payslip_list_api():
    try:
        payslips = get_payslip_list()
        return jsonify(payslips), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 2. API Xem chi tiết Payslip (bao gồm Allowance và Deduction)
@payslip_bp.route('/api/payslips/<string:payslip_id>', methods=['GET'])
def get_payslip_detail_api(payslip_id):
    try:
        payslip_detail = get_payslip_detail(payslip_id)
        if payslip_detail:
            return jsonify(payslip_detail), 200
        else:
            return jsonify({"error": "Payslip not found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 3. API Tạo mới Payslip
@payslip_bp.route('/api/payslips', methods=['POST'])
def create_payslip_api():
    try:
        # Lấy dữ liệu từ request body (JSON)
        payslip_data = request.json

        # Kiểm tra các trường bắt buộc
        required_fields = ['employeeID', 'year', 'month','basicSalary', 'netSalary']
        for field in required_fields:
            if field not in payslip_data:
                return jsonify({"error": f"Missing required field: {field}"}), 400

        # Gọi hàm tạo Payslip
        success = create_payslip(payslip_data)
        if success:
            return jsonify({"message": "Payslip created successfully"}), 201
        else:
            return jsonify({"error": "Failed to create payslip"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 4. API Cập nhật Payslip
@payslip_bp.route('/api/payslips/<string:payslip_id>', methods=['PUT'])
def update_payslip_api(payslip_id):
    try:
        # Lấy dữ liệu từ request body (JSON)
        updated_data = request.json

        # Kiểm tra xem có dữ liệu cần cập nhật hay không
        if not updated_data:
            return jsonify({"error": "No data provided for update"}), 400

        # Gọi hàm cập nhật Payslip
        success = update_payslip(payslip_id, updated_data)
        if success:
            return jsonify({"message": "Payslip updated successfully"}), 200
        else:
            return jsonify({"error": "Failed to update payslip"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 5. API Xóa Payslip
@payslip_bp.route('/api/payslips/<string:payslip_id>', methods=['DELETE'])
def delete_payslip_api(payslip_id):
    try:
        # Gọi hàm xóa Payslip
        success = delete_payslip(payslip_id)
        if success:
            return jsonify({"message": "Payslip deleted successfully"}), 200
        else:
            return jsonify({"error": "Payslip not found or failed to delete"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500