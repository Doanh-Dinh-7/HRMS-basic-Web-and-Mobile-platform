package com.example.hrms_mobile.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrms_mobile.R;
import com.example.hrms_mobile.util.DateTimeUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class AddEmployeeActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextInputEditText etEmployeeId, etFullName, etPosition, etDepartment, etManager;
    private TextInputEditText etGender, etDateOfBirth, etNationality, etIdNumber, etEmail, etPhone, etHealthInsurance;
    private TextInputEditText etAddress, etCountry, etCity, etState, etPostalCode;
    private TextInputEditText etBankName, etAccountNumber;
    private Button btnSave;

    private Date selectedDateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        // Initialize views
        initViews();

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
                // In a real app, save the employee data to the database
                Toast.makeText(this, "New employee added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
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

