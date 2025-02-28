from app import app
from routes.sach import sach_bp  # Import Blueprint từ nhanVien.py

# Đăng ký Blueprint vào Flask app
app.register_blueprint(sach_bp)
