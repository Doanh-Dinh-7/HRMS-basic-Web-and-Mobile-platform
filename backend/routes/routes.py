from app import app
from routes.employee import employee_bp
from routes.timekeeping import timekeeping_bp
from routes.payslip import payslip_bp
from routes.mobile_timekeeping import mobile_timekeeping_bp
from routes.department import department_bp

# Đăng ký Blueprint vào Flask app
app.register_blueprint(employee_bp)
app.register_blueprint(timekeeping_bp)
app.register_blueprint(payslip_bp)
app.register_blueprint(mobile_timekeeping_bp)
app.register_blueprint(department_bp)
