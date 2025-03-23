package com.example.hrms_mobile.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hrms_mobile.R;
import com.example.hrms_mobile.model.Employee;
import com.example.hrms_mobile.util.DateTimeUtil;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

public class EmployeeDetailsActivity extends AppCompatActivity {
    private ImageView ivBack;
    private ImageView ivAvatar;
    private TextView tvName, tvPosition, tvEmail, tvPhone, tvDepartment, tvManager, tvEmployeeId;
    private TextView tvStatus, tvFullName, tvGender, tvDateOfBirth, tvNationality, tvIdNumber, tvHealthInsurance;
    private TextView tvAddress, tvCountry, tvCity, tvState, tvPostalCode;
    private TextView tvBankName, tvAccountName, tvAccountNumber;
    private MaterialButton btnEdit, btnDelete;

    private String employeeId;
    private Map<String, Employee> employeesMap; // Simulating a database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        // Initialize views
        initViews();

        // Get employee ID from intent
        employeeId = getIntent().getStringExtra("EMPLOYEE_ID");

        // Setup sample data
        setupSampleData();

        // Load employee data
        Employee employee = employeesMap.get(employeeId);
        if (employee != null) {
            displayEmployeeDetails(employee);
        }

        // Set click listeners
        setClickListeners();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        ivAvatar = findViewById(R.id.iv_avatar);
        tvName = findViewById(R.id.tv_name);
        tvPosition = findViewById(R.id.tv_position);
        tvEmail = findViewById(R.id.tv_email);
        tvPhone = findViewById(R.id.tv_phone);
        tvDepartment = findViewById(R.id.tv_department);
        tvManager = findViewById(R.id.tv_manager);
        tvEmployeeId = findViewById(R.id.tv_employee_id);
        tvStatus = findViewById(R.id.tv_status);

        // Personal information
        tvFullName = findViewById(R.id.tv_full_name);
        tvGender = findViewById(R.id.tv_gender);
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);
        tvNationality = findViewById(R.id.tv_nationality);
        tvIdNumber = findViewById(R.id.tv_id_number);
        tvHealthInsurance = findViewById(R.id.tv_health_insurance);

        // Address information
        tvAddress = findViewById(R.id.tv_address);
        tvCountry = findViewById(R.id.tv_country);
        tvCity = findViewById(R.id.tv_city);
        tvState = findViewById(R.id.tv_state);
        tvPostalCode = findViewById(R.id.tv_postal_code);

        // Bank information
        tvBankName = findViewById(R.id.tv_bank_name);
        tvAccountName = findViewById(R.id.tv_account_name);
        tvAccountNumber = findViewById(R.id.tv_account_number);

        // Buttons
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);
    }

    private void setClickListeners() {
        ivBack.setOnClickListener(v -> finish());

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
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this employee?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Remove employee from the list
            employeesMap.remove(employeeId);
            Toast.makeText(this, "Employee deleted", Toast.LENGTH_SHORT).show();
            finish();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void setupSampleData() {
        // Sample data for demonstration (simulating database)
        employeesMap = new HashMap<>();

        // Employee 1 - Nguyễn Văn An
        Employee nguyenVanAn = new Employee("1", "Nguyễn Văn An", "an@example.com", "0987654321", "IT Specialist", "IT", "John Smith", "");
        nguyenVanAn.setGender("Male");
        nguyenVanAn.setDateOfBirth(DateTimeUtil.parseDate("15/05/1990"));
        nguyenVanAn.setNationality("Vietnam");
        nguyenVanAn.setIdNumber("001090012345");
        nguyenVanAn.setHealthInsuranceNumber("SH0123456789");
        nguyenVanAn.setAddress("123 Le Loi Street, District 1");
        nguyenVanAn.setCountry("Vietnam");
        nguyenVanAn.setCity("Ho Chi Minh City");
        nguyenVanAn.setState("Ho Chi Minh City");
        nguyenVanAn.setPostalCode("70000");
        nguyenVanAn.setBankName("Vietcombank");
        nguyenVanAn.setAccountName("NGUYEN VAN AN");
        nguyenVanAn.setAccountNumber("1234567890");
        nguyenVanAn.setActive(true);
        employeesMap.put("1", nguyenVanAn);

        // Employee 2 - Trần Thị Bình
        Employee tranThiBinh = new Employee("2", "Trần Thị Bình", "binh@example.com", "0876543210", "UX Designer", "Design", "John Smith", "");
        tranThiBinh.setGender("Female");
        tranThiBinh.setDateOfBirth(DateTimeUtil.parseDate("20/08/1992"));
        tranThiBinh.setNationality("Vietnam");
        tranThiBinh.setIdNumber("001092023456");
        tranThiBinh.setHealthInsuranceNumber("SH0234567890");
        tranThiBinh.setAddress("456 Nguyen Hue Street, District 1");
        tranThiBinh.setCountry("Vietnam");
        tranThiBinh.setCity("Ho Chi Minh City");
        tranThiBinh.setState("Ho Chi Minh City");
        tranThiBinh.setPostalCode("70000");
        tranThiBinh.setBankName("BIDV");
        tranThiBinh.setAccountName("TRAN THI BINH");
        tranThiBinh.setAccountNumber("2345678901");
        tranThiBinh.setActive(true);
        employeesMap.put("2", tranThiBinh);

        // Employee 3 - Lê Văn Cường
        Employee leVanCuong = new Employee("3", "Lê Văn Cường", "cuong@example.com", "0987654320", "Developer", "IT", "John Smith", "");
        leVanCuong.setGender("Male");
        leVanCuong.setDateOfBirth(DateTimeUtil.parseDate("10/03/1988"));
        leVanCuong.setNationality("Vietnam");
        leVanCuong.setIdNumber("001088034567");
        leVanCuong.setHealthInsuranceNumber("SH0345678901");
        leVanCuong.setAddress("789 Ly Tu Trong Street, District 1");
        leVanCuong.setCountry("Vietnam");
        leVanCuong.setCity("Ho Chi Minh City");
        leVanCuong.setState("Ho Chi Minh City");
        leVanCuong.setPostalCode("70000");
        leVanCuong.setBankName("Techcombank");
        leVanCuong.setAccountName("LE VAN CUONG");
        leVanCuong.setAccountNumber("3456789012");
        leVanCuong.setActive(true);
        employeesMap.put("3", leVanCuong);

        // Employee 4 - Phạm Thị Dung
        Employee phamThiDung = new Employee("4", "Phạm Thị Dung", "dung@example.com", "0912345678", "HR Manager", "HR", "John Smith", "");
        phamThiDung.setGender("Female");
        phamThiDung.setDateOfBirth(DateTimeUtil.parseDate("25/11/1985"));
        phamThiDung.setNationality("Vietnam");
        phamThiDung.setIdNumber("001085045678");
        phamThiDung.setHealthInsuranceNumber("SH0456789012");
        phamThiDung.setAddress("101 Nguyen Du Street, District 1");
        phamThiDung.setCountry("Vietnam");
        phamThiDung.setCity("Ho Chi Minh City");
        phamThiDung.setState("Ho Chi Minh City");
        phamThiDung.setPostalCode("70000");
        phamThiDung.setBankName("Agribank");
        phamThiDung.setAccountName("PHAM THI DUNG");
        phamThiDung.setAccountNumber("4567890123");
        phamThiDung.setActive(true);
        employeesMap.put("4", phamThiDung);

        // Employee 5 - Trần Văn Quản
        Employee tranVanQuan = new Employee("5", "Trần Văn Quản", "quan@example.com", "0861234567", "CEO", "Executive", "", "");
        tranVanQuan.setGender("Male");
        tranVanQuan.setDateOfBirth(DateTimeUtil.parseDate("05/07/1980"));
        tranVanQuan.setNationality("Vietnam");
        tranVanQuan.setIdNumber("001080056789");
        tranVanQuan.setHealthInsuranceNumber("SH0567890123");
        tranVanQuan.setAddress("202 Dong Khoi Street, District 1");
        tranVanQuan.setCountry("Vietnam");
        tranVanQuan.setCity("Ho Chi Minh City");
        tranVanQuan.setState("Ho Chi Minh City");
        tranVanQuan.setPostalCode("70000");
        tranVanQuan.setBankName("VPBank");
        tranVanQuan.setAccountName("TRAN VAN QUAN");
        tranVanQuan.setAccountNumber("5678901234");
        tranVanQuan.setActive(true);
        employeesMap.put("5", tranVanQuan);
    }

    private void displayEmployeeDetails(Employee employee) {
        tvName.setText(employee.getFullName());
        tvPosition.setText(employee.getPosition());
        tvEmail.setText(employee.getEmail());
        tvPhone.setText(employee.getPhoneNumber());
        tvDepartment.setText(employee.getDepartment());

        // Change from "Manager" to "Position"
        TextView managerLabel = findViewById(R.id.tv_manager_label);
        if (managerLabel != null) {
            managerLabel.setText("Position");
        }
        tvManager.setText(employee.getPosition());

        tvEmployeeId.setText(employee.getId());

        // Set status
        if (employee.isActive()) {
            tvStatus.setText("Active");
            tvStatus.setBackgroundResource(R.drawable.bg_status_active);
            tvStatus.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvStatus.setText("Inactive");
            tvStatus.setBackgroundResource(R.drawable.bg_status_inactive);
            tvStatus.setTextColor(getResources().getColor(R.color.red));
        }

        // Personal information
        tvFullName.setText(employee.getFullName());
        tvGender.setText(employee.getGender() != null ? employee.getGender() : "-");
        tvDateOfBirth.setText(employee.getDateOfBirth() != null ? DateTimeUtil.formatDate(employee.getDateOfBirth()) : "-");
        tvNationality.setText(employee.getNationality() != null ? employee.getNationality() : "-");
        tvIdNumber.setText(employee.getIdNumber() != null ? employee.getIdNumber() : "-");
        tvHealthInsurance.setText(employee.getHealthInsuranceNumber() != null ? employee.getHealthInsuranceNumber() : "-");

        // Address information
        tvAddress.setText(employee.getAddress() != null ? employee.getAddress() : "-");
        tvCountry.setText(employee.getCountry() != null ? employee.getCountry() : "-");
        tvCity.setText(employee.getCity() != null ? employee.getCity() : "-");
        tvState.setText(employee.getState() != null ? employee.getState() : "-");
        tvPostalCode.setText(employee.getPostalCode() != null ? employee.getPostalCode() : "-");

        // Bank information
        tvBankName.setText(employee.getBankName() != null ? employee.getBankName() : "-");
        tvAccountName.setText(employee.getAccountName() != null ? employee.getAccountName() : "-");
        tvAccountNumber.setText(employee.getAccountNumber() != null ? employee.getAccountNumber() : "-");

        // Load avatar image
        if (employee.getAvatar() != null && !employee.getAvatar().isEmpty()) {
            Glide.with(this)
                    .load(employee.getAvatar())
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(ivAvatar);
        } else {
            ivAvatar.setImageResource(R.drawable.ic_person);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when coming back to this activity (after editing)
        Employee employee = employeesMap.get(employeeId);
        if (employee != null) {
            displayEmployeeDetails(employee);
        }
    }
}

