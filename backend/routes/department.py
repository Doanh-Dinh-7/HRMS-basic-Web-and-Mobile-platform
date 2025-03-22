from flask import Blueprint, jsonify, request
from models import get_department_list

department_bp = Blueprint('department', __name__)

@department_bp.route('/api/departments', methods=['GET'])
def get_department_list_api():
    try:
        departments = get_department_list()
        return jsonify(departments), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


