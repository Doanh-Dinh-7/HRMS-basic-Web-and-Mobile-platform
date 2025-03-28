package com.example.hrms_mobile.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hrms_mobile.R;
import com.example.hrms_mobile.model.ApiResponse;
import com.example.hrms_mobile.model.Employee;
import com.example.hrms_mobile.network.ApiService;
import com.example.hrms_mobile.network.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class EmployeeDetailsActivity extends AppCompatActivity {
    private ImageView ivBack, ivAvatar;
    private TextView tvName, tvPosition, tvStatus;
    private TextView tvEmail, tvPhone;
    private TextView tvDepartment, tvManager, tvEmployeeId;
    private TextView tvFullName, tvGender, tvDateOfBirth, tvNationality, tvIdNumber, tvHealthInsurance;
    private TextView tvAddress, tvCountry, tvCity, tvState, tvPostalCode;
    private TextView tvBankName, tvAccountName, tvAccountNumber;
    private MaterialButton btnEdit, btnDelete;
    private ProgressBar progressBar;

    private String employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        initViews();
        employeeId = getIntent().getStringExtra("EMPLOYEE_ID");
        if (employeeId != null) {
            loadEmployeeDetails(employeeId);
        }

        ivBack.setOnClickListener(v -> finish());
        setClickListeners();
    }

    private void initViews() {
        // Header
        ivBack = findViewById(R.id.iv_back);

        // Profile Card
        ivAvatar = findViewById(R.id.iv_avatar);
        tvName = findViewById(R.id.tv_name);
        tvPosition = findViewById(R.id.tv_position);
        tvStatus = findViewById(R.id.tv_status);

        // Contact Card
        tvEmail = findViewById(R.id.tv_email);
        tvPhone = findViewById(R.id.tv_phone);

        // Work Information
        tvDepartment = findViewById(R.id.tv_department);
        tvManager = findViewById(R.id.tv_manager);
        tvEmployeeId = findViewById(R.id.tv_employee_id);

        // Personal Information
        tvFullName = findViewById(R.id.tv_full_name);
        tvGender = findViewById(R.id.tv_gender);
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);
        tvNationality = findViewById(R.id.tv_nationality);
        tvIdNumber = findViewById(R.id.tv_id_number);
        tvHealthInsurance = findViewById(R.id.tv_health_insurance);

        // Address Information
        tvAddress = findViewById(R.id.tv_address);
        tvCountry = findViewById(R.id.tv_country);
        tvCity = findViewById(R.id.tv_city);
        tvState = findViewById(R.id.tv_state);
        tvPostalCode = findViewById(R.id.tv_postal_code);

        // Bank Information
        tvBankName = findViewById(R.id.tv_bank_name);
        tvAccountName = findViewById(R.id.tv_account_name);
        tvAccountNumber = findViewById(R.id.tv_account_number);

        // Buttons
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);

        // Thêm khởi tạo ProgressBar
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setClickListeners() {
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditEmployeeActivity.class);
            intent.putExtra("EMPLOYEE_ID", employeeId);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog();
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này không?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            deleteEmployee();
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void deleteEmployee() {
        showLoading(true);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.deleteEmployee(employeeId).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                showLoading(false);
                if (response.isSuccessful()) {
                    Toast.makeText(EmployeeDetailsActivity.this,
                            "Xóa nhân viên thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        showError("Lỗi: " + errorBody);
                    } catch (IOException e) {
                        showError("Không thể xóa nhân viên");
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                showLoading(false);
                showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void loadEmployeeDetails(String employeeId) {
        showLoading(true);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getEmployeeDetail(employeeId).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                showLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    displayEmployeeDetails(response.body());
                } else {
                    showError("Không thể tải thông tin nhân viên");
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                showLoading(false);
                showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void displayEmployeeDetails(Employee employee) {
        // Profile Card
        if (employee.getAvatar() != null && !employee.getAvatar().isEmpty()) {
            Glide.with(this)
                    .load(employee.getAvatar())
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(ivAvatar);
        }
        tvName.setText(employee.getFullName());
        tvPosition.setText(employee.getPosition());

        // Sửa phần status - thay vì dùng getStatus(), chúng ta sẽ dùng
        // getEmploymentStatus()
        String status = employee.getStatus(); // Sửa tên method
        tvStatus.setText(status);
        if ("ACTIVE".equalsIgnoreCase(status)) { // Thêm equalsIgnoreCase để linh hoạt hơn
            tvStatus.setBackgroundResource(R.drawable.bg_status_active);
            tvStatus.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvStatus.setBackgroundResource(R.drawable.bg_status_inactive);
            tvStatus.setTextColor(getResources().getColor(R.color.red));
        }

        // Contact Information
        tvEmail.setText(employee.getEmail());
        tvPhone.setText(employee.getPhoneNumber());

        // Work Information
        tvDepartment.setText(employee.getDepartmentName());
        tvManager.setText(employee.getPosition());
        tvEmployeeId.setText(employee.getEmployeeId());

        // Personal Information
        tvFullName.setText(employee.getFullName());
        tvGender.setText(employee.getGender());
        tvDateOfBirth.setText(employee.getDateOfBirth());
        tvNationality.setText(employee.getNationality());
        tvIdNumber.setText(employee.getNationalIDNumber());
        tvHealthInsurance.setText(employee.getHealthInsuranceNumber());

        // Address Information
        tvAddress.setText(employee.getAddress());
        // Thêm các trường địa chỉ chi tiết nếu có trong model Employee
        tvCountry.setText(employee.getCountry());
        tvCity.setText(employee.getCity());
        tvState.setText(employee.getState());
        tvPostalCode.setText(employee.getPostalCode());

        // Bank Information
        tvBankName.setText(employee.getBankName());
        tvAccountName.setText(employee.getFullName()); // Giả sử tên tài khoản giống tên đầy đủ
        tvAccountNumber.setText(employee.getBankName());
    }

    private void showLoading(boolean isLoading) {
        if (progressBar != null) { // Thêm kiểm tra null
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
