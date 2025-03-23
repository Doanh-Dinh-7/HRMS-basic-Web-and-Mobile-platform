package com.example.hrms_mobile.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrms_mobile.R;
import com.example.hrms_mobile.model.Payslip;
import com.example.hrms_mobile.util.CurrencyFormatter;
import com.example.hrms_mobile.util.DateTimeUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PayslipDetailsActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvEmployeeName, tvPosition, tvMonth, tvPaymentDate, tvGrossSalary;
    private TextView tvBasicSalary, tvOvertimePay, tvBonus, tvOtherAllowances;
    private TextView tvIncomeTax, tvSocialInsurance, tvHealthInsurance, tvOtherDeductions;
    private TextView tvNetSalary;

    private String payslipId;
    private Map<String, Payslip> payslipsMap; // Simulating a database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payslip_details);

        // Initialize views
        ivBack = findViewById(R.id.iv_back);
        tvEmployeeName = findViewById(R.id.tv_employee_name);
        tvPosition = findViewById(R.id.tv_position);
        tvMonth = findViewById(R.id.tv_month);
        tvPaymentDate = findViewById(R.id.tv_payment_date);
        tvGrossSalary = findViewById(R.id.tv_gross_salary);
        tvBasicSalary = findViewById(R.id.tv_basic_salary);
        tvOvertimePay = findViewById(R.id.tv_overtime_pay);
        tvBonus = findViewById(R.id.tv_bonus);
        tvOtherAllowances = findViewById(R.id.tv_other_allowances);
        tvIncomeTax = findViewById(R.id.tv_income_tax);
        tvSocialInsurance = findViewById(R.id.tv_social_insurance);
        tvHealthInsurance = findViewById(R.id.tv_health_insurance);
        tvOtherDeductions = findViewById(R.id.tv_other_deductions);
        tvNetSalary = findViewById(R.id.tv_net_salary);

        // Get payslip ID from intent
        payslipId = getIntent().getStringExtra("PAYSLIP_ID");

        // Setup sample data
        setupSampleData();

        // Load payslip data
        Payslip payslip = payslipsMap.get(payslipId);
        if (payslip != null) {
            displayPayslipDetails(payslip);
        }

        // Set click listeners
        ivBack.setOnClickListener(v -> finish());
    }

    private void setupSampleData() {
        // Sample data for demonstration (simulating database)
        payslipsMap = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        Date janDate = cal.getTime();

        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        Date febDate = cal.getTime();

        payslipsMap.put("1", new Payslip("1", "1", "Nguyễn Văn An", "Chuyên viên IT", "Tháng 1",
                cal.get(Calendar.YEAR), janDate, 15000000, 10000000, 1000000,
                2000000, 500000, 1000000, 500000, 300000, 200000));

        payslipsMap.put("2", new Payslip("2", "1", "Nguyễn Văn An", "Chuyên viên IT", "Tháng 2",
                cal.get(Calendar.YEAR), febDate, 15500000, 10000000, 1500000,
                2000000, 500000, 1000000, 500000, 300000, 200000));

        payslipsMap.put("3", new Payslip("3", "2", "Trần Thị Bình", "Thiết kế UX", "Tháng 1",
                cal.get(Calendar.YEAR), janDate, 14000000, 9000000, 800000,
                2000000, 500000, 900000, 450000, 270000, 180000));
    }

    private void displayPayslipDetails(Payslip payslip) {
        tvEmployeeName.setText(payslip.getEmployeeName());
        tvPosition.setText(payslip.getPosition());
        tvMonth.setText(payslip.getMonth() + " " + payslip.getYear());
        tvPaymentDate.setText(DateTimeUtil.formatDate(payslip.getPaymentDate()));

        tvGrossSalary.setText(CurrencyFormatter.format(payslip.getGrossSalary()));
        tvBasicSalary.setText(CurrencyFormatter.format(payslip.getBasicSalary()));
        tvOvertimePay.setText(CurrencyFormatter.format(payslip.getOvertimePay()));
        tvBonus.setText(CurrencyFormatter.format(payslip.getBonus()));
        tvOtherAllowances.setText(CurrencyFormatter.format(payslip.getOtherAllowances()));

        tvIncomeTax.setText(CurrencyFormatter.format(payslip.getIncomeTax()));
        tvSocialInsurance.setText(CurrencyFormatter.format(payslip.getSocialInsurance()));
        tvHealthInsurance.setText(CurrencyFormatter.format(payslip.getHealthInsurance()));
        tvOtherDeductions.setText(CurrencyFormatter.format(payslip.getOtherDeductions()));

        tvNetSalary.setText(CurrencyFormatter.format(payslip.getNetSalaryReceived()));
    }
}

