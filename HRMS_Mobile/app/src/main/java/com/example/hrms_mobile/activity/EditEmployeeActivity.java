package com.example.hrms_mobile.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrms_mobile.R;
import com.example.hrms_mobile.model.Employee;
import com.example.hrms_mobile.util.DateTimeUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditEmployeeActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextInputEditText etEmployeeId, etFullName, etPosition, etDepartment, etManager;
    private TextInputEditText etGender, etDateOfBirth, etNationality, etIdNumber, etEmail, etPhone, etHealthInsurance;
    private TextInputEditText etAddress, etCountry, etCity, etState, etPostalCode;
    private TextInputEditText etBankName, etAccountNumber;
    private Button btnSave;

    private String employeeId;
    private Date selectedDateOfBirth;
    private Map<String, Employee> employeesMap; // Simulating a database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        // Initialize views
        initViews();

        // Get employee ID from intent
        employeeId = getIntent().getStringExtra("EMPLOYEE_ID");

        // Setup sample data
        setupSampleData();

        // Load employee data
        Employee employee = employeesMap.get(employeeId);
        if (employee != null) {
            loadEmployeeData(employee);
        }

        // Set click listeners
        setClickListeners();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);

        // Basic information
        etEmployeeId = findViewById(R.id.et_employee_id);
        etFullName = findViewById(R.id.et_full_name);
        etPosition = findViewById(R.id.et_position);
        etDepartment = findViewById(R.id.et_department);
        etManager = findViewById(R.id.et_manager);

        // Personal information
        etGender = findViewById(R.id.et_gender);
        etDateOfBirth = findViewById(R.id.et_date_of_birth);
        etNationality = findViewById(R.id.et_nationality);
        etIdNumber = findViewById(R.id.et_id_number);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etHealthInsurance = findViewById(R.id.et_health_insurance);

        // Address information
        etAddress = findViewById(R.id.et_address);
        etCountry = findViewById(R.id.et_country);
        etCity = findViewById(R.id.et_city);
        etState = findViewById(R.id.et_state);
        etPostalCode = findViewById(R.id.et_postal_code);

        // Bank information
        etBankName = findViewById(R.id.et_bank_name);
        etAccountNumber = findViewById(R.id.et_account_number);

        btnSave = findViewById(R.id.btn_save);
    }

    private void setClickListeners() {
        ivBack.setOnClickListener(v -> finish());

        // Date of birth picker
        etDateOfBirth.setOnClickListener(v -> showDatePickerDialog());

        btnSave.setOnClickListener(v -> {
            // Validate required fields
            if (validateForm()) {
                // Update employee data
                updateEmployeeData();
                Toast.makeText(this, "Employee information updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        if (selectedDateOfBirth != null) {
            calendar.setTime(selectedDateOfBirth);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
                    selectedDateOfBirth = selectedCalendar.getTime();
                    etDateOfBirth.setText(DateTimeUtil.formatDate(selectedDateOfBirth));
                },
                year, month, day
        );

        // Set max date to today
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
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

    private void loadEmployeeData(Employee employee) {
        // Basic information
        etEmployeeId.setText(employee.getId());
        etFullName.setText(employee.getFullName());
        etPosition.setText(employee.getPosition());
        etDepartment.setText(employee.getDepartment());
        etManager.setText(employee.getManager());

        // Personal information
        etGender.setText(employee.getGender());
        if (employee.getDateOfBirth() != null) {
            selectedDateOfBirth = employee.getDateOfBirth();
            etDateOfBirth.setText(DateTimeUtil.formatDate(employee.getDateOfBirth()));
        }
        etNationality.setText(employee.getNationality());
        etIdNumber.setText(employee.getIdNumber());
        etEmail.setText(employee.getEmail());
        etPhone.setText(employee.getPhoneNumber());
        etHealthInsurance.setText(employee.getHealthInsuranceNumber());

        // Address information
        etAddress.setText(employee.getAddress());
        etCountry.setText(employee.getCountry());
        etCity.setText(employee.getCity());
        etState.setText(employee.getState());
        etPostalCode.setText(employee.getPostalCode());

        // Bank information
        etBankName.setText(employee.getBankName());
        etAccountNumber.setText(employee.getAccountNumber());
    }

    private void updateEmployeeData() {
        Employee employee = employeesMap.get(employeeId);
        if (employee != null) {
            // Basic information
            employee.setId(etEmployeeId.getText().toString().trim());
            employee.setFullName(etFullName.getText().toString().trim());
            employee.setPosition(etPosition.getText().toString().trim());
            employee.setDepartment(etDepartment.getText().toString().trim());
            employee.setManager(etManager.getText().toString().trim());

            // Personal information
            employee.setGender(etGender.getText().toString().trim());
            employee.setDateOfBirth(selectedDateOfBirth);
            employee.setNationality(etNationality.getText().toString().trim());
            employee.setIdNumber(etIdNumber.getText().toString().trim());
            employee.setEmail(etEmail.getText().toString().trim());
            employee.setPhoneNumber(etPhone.getText().toString().trim());
            employee.setHealthInsuranceNumber(etHealthInsurance.getText().toString().trim());

            // Address information
            employee.setAddress(etAddress.getText().toString().trim());
            employee.setCountry(etCountry.getText().toString().trim());
            employee.setCity(etCity.getText().toString().trim());
            employee.setState(etState.getText().toString().trim());
            employee.setPostalCode(etPostalCode.getText().toString().trim());

            // Bank information
            employee.setBankName(etBankName.getText().toString().trim());
            employee.setAccountNumber(etAccountNumber.getText().toString().trim());
        }
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Validate required fields
        if (etFullName.getText().toString().trim().isEmpty()) {
            etFullName.setError("Full name is required");
            isValid = false;
        }

        if (etPosition.getText().toString().trim().isEmpty()) {
            etPosition.setError("Position is required");
            isValid = false;
        }

        if (etDepartment.getText().toString().trim().isEmpty()) {
            etDepartment.setError("Department is required");
            isValid = false;
        }

        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Email is required");
            isValid = false;
        }

        if (etPhone.getText().toString().trim().isEmpty()) {
            etPhone.setError("Phone number is required");
            isValid = false;
        }

        return isValid;
    }
}

