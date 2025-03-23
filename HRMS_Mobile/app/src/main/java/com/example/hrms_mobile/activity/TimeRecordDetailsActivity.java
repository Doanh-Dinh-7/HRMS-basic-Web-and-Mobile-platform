package com.example.hrms_mobile.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrms_mobile.R;
import com.example.hrms_mobile.model.TimeRecord;
import com.example.hrms_mobile.util.DateTimeUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeRecordDetailsActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvEmployeeName, tvDate, tvTimeStart, tvTimeEnd, tvDepartment, tvShift, tvTaskType, tvNotes, tvStatus;
    private Button btnCheckout;

    private String timeRecordId;
    private Map<String, TimeRecord> timeRecordsMap; // Simulating a database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_record_details);

        // Initialize views
        ivBack = findViewById(R.id.iv_back);
        tvEmployeeName = findViewById(R.id.tv_employee_name);
        tvDate = findViewById(R.id.tv_date);
        tvTimeStart = findViewById(R.id.tv_time_start);
        tvTimeEnd = findViewById(R.id.tv_time_end);
        tvDepartment = findViewById(R.id.tv_department);
        tvShift = findViewById(R.id.tv_shift);
        tvTaskType = findViewById(R.id.tv_task_type);
        tvNotes = findViewById(R.id.tv_notes);
        tvStatus = findViewById(R.id.tv_status);
        btnCheckout = findViewById(R.id.btn_checkout);

        // Get time record ID from intent
        timeRecordId = getIntent().getStringExtra("TIME_RECORD_ID");

        // Setup sample data
        setupSampleData();

        // Load time record data
        TimeRecord timeRecord = timeRecordsMap.get(timeRecordId);
        if (timeRecord != null) {
            displayTimeRecordDetails(timeRecord);

            // Only show checkout button for records that are "On shift"
            if ("On shift".equals(timeRecord.getStatus())) {
                btnCheckout.setEnabled(true);
            } else {
                btnCheckout.setEnabled(false);
            }
        }

        // Set click listeners
        ivBack.setOnClickListener(v -> finish());

        btnCheckout.setOnClickListener(v -> {
            // In a real app, update the time record in the database
            TimeRecord record = timeRecordsMap.get(timeRecordId);
            if (record != null) {
                record.setTimeEnd(Calendar.getInstance().getTime());
                record.setStatus("End of shift");
                displayTimeRecordDetails(record);
                btnCheckout.setEnabled(false);
                Toast.makeText(this, "Checkout confirmed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSampleData() {
        // Sample data for demonstration (simulating database)
        timeRecordsMap = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date twoDaysAgo = cal.getTime();

        timeRecordsMap.put("1", new TimeRecord("1", "1", "Nguyễn Văn An", today, today, null, "IT", "Morning", "Regular", "", "On shift"));
        timeRecordsMap.put("2", new TimeRecord("2", "1", "Nguyễn Văn An", yesterday, yesterday, yesterday, "IT", "Morning", "Regular", "", "End of shift"));
        timeRecordsMap.put("3", new TimeRecord("3", "2", "Trần Thị Bình", today, today, null, "Design", "Morning", "Regular", "", "On shift"));
        timeRecordsMap.put("4", new TimeRecord("4", "3", "Lê Văn Cường", twoDaysAgo, twoDaysAgo, twoDaysAgo, "IT", "Morning", "Regular", "", "End of shift"));
    }

    private void displayTimeRecordDetails(TimeRecord timeRecord) {
        tvEmployeeName.setText(timeRecord.getEmployeeName());
        tvDate.setText(DateTimeUtil.formatDate(timeRecord.getDate()));
        tvTimeStart.setText(DateTimeUtil.formatTime(timeRecord.getTimeStart()));

        if (timeRecord.getTimeEnd() != null) {
            tvTimeEnd.setText(DateTimeUtil.formatTime(timeRecord.getTimeEnd()));
        } else {
            tvTimeEnd.setText("-");
        }

        tvDepartment.setText(timeRecord.getDepartment());
        tvShift.setText(timeRecord.getShift());
        tvTaskType.setText(timeRecord.getTaskType());

        if (timeRecord.getNotes() != null && !timeRecord.getNotes().isEmpty()) {
            tvNotes.setText(timeRecord.getNotes());
        } else {
            tvNotes.setText("-");
        }

        tvStatus.setText(timeRecord.getStatus());

        // Set tag color based on status
        if ("On shift".equals(timeRecord.getStatus())) {
            tvStatus.setBackgroundResource(R.drawable.bg_status_active);
        } else {
            tvStatus.setBackgroundResource(R.drawable.bg_status_inactive);
        }
    }
}

