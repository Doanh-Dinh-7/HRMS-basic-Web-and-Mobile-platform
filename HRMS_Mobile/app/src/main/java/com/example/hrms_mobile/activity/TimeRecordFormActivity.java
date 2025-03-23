package com.example.hrms_mobile.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrms_mobile.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeRecordFormActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvDate, tvTimeStart;
    private Spinner spinnerEmployee, spinnerDepartment, spinnerShift, spinnerTaskType;
    private EditText etNotes;
    private Button btnCancel, btnSave;

    private Calendar selectedDate = Calendar.getInstance();
    private Calendar selectedStartTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_record_form);

        // Initialize views
        ivBack = findViewById(R.id.iv_back);
        tvDate = findViewById(R.id.tv_date);
        tvTimeStart = findViewById(R.id.tv_time_start);
        spinnerEmployee = findViewById(R.id.spinner_employee);
        spinnerDepartment = findViewById(R.id.spinner_department);
        spinnerShift = findViewById(R.id.spinner_shift);
        spinnerTaskType = findViewById(R.id.spinner_task_type);
        etNotes = findViewById(R.id.et_notes);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);

        // Set up spinners
        setupSpinners();

        // Set initial date and time
        updateDateDisplay();
        updateTimeDisplay();

        // Set click listeners
        ivBack.setOnClickListener(v -> finish());

        tvDate.setOnClickListener(v -> showDatePicker());

        tvTimeStart.setOnClickListener(v -> showTimePicker());

        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            // In a real app, save the time record to the database
            Toast.makeText(this, "Time record saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void setupSpinners() {
        // Employee spinner - keep Vietnamese names
        String[] employees = {"Nguyễn Văn An", "Trần Thị Bình", "Lê Văn Cường", "Phạm Thị Dung", "Trần Văn Quản"};
        ArrayAdapter<String> employeeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employees);
        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee.setAdapter(employeeAdapter);

        // Department spinner
        String[] departments = {"IT", "Design", "HR", "Executive"};
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(departmentAdapter);

        // Shift spinner
        String[] shifts = {"Morning", "Afternoon", "Evening"};
        ArrayAdapter<String> shiftAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shifts);
        shiftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShift.setAdapter(shiftAdapter);

        // Task type spinner
        String[] taskTypes = {"Regular", "Overtime", "Weekend", "Holiday"};
        ArrayAdapter<String> taskTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, taskTypes);
        taskTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskType.setAdapter(taskTypeAdapter);
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate.set(Calendar.YEAR, year);
                    selectedDate.set(Calendar.MONTH, month);
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateDisplay();
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedStartTime.set(Calendar.MINUTE, minute);
                    updateTimeDisplay();
                },
                selectedStartTime.get(Calendar.HOUR_OF_DAY),
                selectedStartTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateDateDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        tvDate.setText(dateFormat.format(selectedDate.getTime()));
    }

    private void updateTimeDisplay() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvTimeStart.setText(timeFormat.format(selectedStartTime.getTime()));
    }
}

