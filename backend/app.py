from flask import Flask, send_from_directory
from flask_sqlalchemy import SQLAlchemy
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

# Api  routes
from routes import routes

if __name__ == '__main__':
    # Chạy server trên tất cả các interface (0.0.0.0) để Android có thể kết nối
    app.run(host='0.0.0.0', port=5000, debug=True)
 