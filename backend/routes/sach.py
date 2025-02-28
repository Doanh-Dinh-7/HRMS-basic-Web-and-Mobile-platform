from flask import Blueprint, jsonify, request
from models import search_TenSach, search_MaTacGia, search_TheLoai, get_all_sach

# Tạo Blueprint cho các API liên quan đến nhân viên
sach_bp = Blueprint('sach', __name__)

@sach_bp.route('/api/search', methods=['GET'])
def get_all_sach_api():
    try:
        all_Sach = get_all_sach()
        return jsonify(all_Sach), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    

@sach_bp.route('/api/search/tensach/<string:tenSach>', methods=['GET'])
def search_TenSach_api(tenSach):
    try:
        sach = search_TenSach(tenSach)
        return jsonify(sach), 200
    except Exception as e:
        return jsonify({'message_error': str(e)}), 500

@sach_bp.route('/api/search/tacgia/<string:maTacGia>', methods=['GET'])
def search_MaTacGia_api(maTacGia):
    try:
        sach = search_MaTacGia(maTacGia)
        return jsonify(sach), 200
    except Exception as e:
        return jsonify({'message_error': str(e)}), 500
    
@sach_bp.route('/api/search/theloai/<string:theLoai>', methods=['GET'])
def search_TheLoai_api(theLoai):
    try:
        sach = search_TheLoai(theLoai)
        return jsonify(sach), 200
    except Exception as e:
        return jsonify({'message_error': str(e)}), 500

# @nhanVien_bp.route('/api/nhanvien/<string:maNV>', methods=['PUT'])
# def update_nhanVien_api(maNV):
#     try:
#         data = request.json
#         result = update_nhanVien(maNV, data['HoTenNV'], data['NgaySinh'], data['GioiTinh'], data['DiaChi'], data['NgayVaoLam'])
        
#         if result is None:
#             return jsonify({"error": "NhanVien not found"}), 404
        
#         return jsonify({'message': 'NhanVien updated successfully'}), 200
#     except Exception as e:
#         return jsonify({'message_error': str(e)}), 500

# @nhanVien_bp.route('/api/nhanvien/<string:maNV>', methods=['DELETE'])
# def delete_nhanVien_api(maNV):
#     try:
#         result = delete_nhanVien(maNV)
        
#         if result is None:
#             return jsonify({"error": "NhanVien not found"}), 404
        
#         return jsonify({'message': 'NhanVien deleted successfully'}), 200
#     except Exception as e:
#         return jsonify({'message_error': str(e)}), 500
    