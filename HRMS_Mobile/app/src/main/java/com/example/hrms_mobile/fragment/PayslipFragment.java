package com.example.hrms_mobile.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_mobile.R;
import com.example.hrms_mobile.adapter.PayslipAdapter;
import com.example.hrms_mobile.model.Payslip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PayslipFragment extends Fragment {
    private RecyclerView recyclerView;
    private PayslipAdapter adapter;
    private TextView tvYearDisplay;
    private View btnPrevYear, btnNextYear;

    private int currentYear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payslip, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        tvYearDisplay = view.findViewById(R.id.tv_year);
        btnPrevYear = view.findViewById(R.id.btn_prev_year);
        btnNextYear = view.findViewById(R.id.btn_next_year);

        // Set current year
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        tvYearDisplay.setText(String.valueOf(currentYear));

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize sample data
        List<Payslip> payslipList = getSamplePayslips(currentYear);
        adapter = new PayslipAdapter(getContext(), payslipList);
        recyclerView.setAdapter(adapter);

        // Setup year navigation
        btnPrevYear.setOnClickListener(v -> {
            currentYear--;
            tvYearDisplay.setText(String.valueOf(currentYear));
            updatePayslipData();
        });

        btnNextYear.setOnClickListener(v -> {
            currentYear++;
            tvYearDisplay.setText(String.valueOf(currentYear));
            updatePayslipData();
        });

        return view;
    }

    private void updatePayslipData() {
        List<Payslip> payslipList = getSamplePayslips(currentYear);
        adapter.updateData(payslipList);
    }

    private List<Payslip> getSamplePayslips(int year) {
        // Sample data for demonstration
        List<Payslip> payslips = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        Date janDate = cal.getTime();

        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        Date febDate = cal.getTime();

        if (year == Calendar.getInstance().get(Calendar.YEAR)) {
            payslips.add(new Payslip("1", "1", "Nguyễn Hoàng", "IT Specialist", "January", year, janDate,
                    15000000, 10000000, 1000000, 2000000, 500000,
                    1000000, 500000, 300000, 200000));

            payslips.add(new Payslip("2", "1", "Nguyễn Hoàng", "IT Specialist", "February", year, febDate,
                    15500000, 10000000, 1500000, 2000000, 500000,
                    1000000, 500000, 300000, 200000));

            payslips.add(new Payslip("3", "2", "Nguyễn Hoàng", "IT Specialist", "March", year, janDate,
                    14000000, 9000000, 800000, 2000000, 500000,
                    900000, 450000, 270000, 180000));

        } else if (year == Calendar.getInstance().get(Calendar.YEAR) - 1) {
            // Previous year data

            payslips.add(new Payslip("4", "1", "Nguyễn Hoàng", "IT Specialist", "January", year, janDate,
                    15000000, 10000000, 1000000, 2000000, 500000,
                    1000000, 500000, 300000, 200000));

            payslips.add(new Payslip("5", "1", "Nguyễn Hoàng", "IT Specialist", "February", year, febDate,
                    15500000, 10000000, 1500000, 2000000, 500000,
                    1000000, 500000, 300000, 200000));

            payslips.add(new Payslip("6", "2", "Nguyễn Hoàng", "IT Specialist", "March", year, janDate,
                    14000000, 9000000, 800000, 2000000, 500000,
                    900000, 450000, 270000, 180000));

            payslips.add(new Payslip("7", "1", "Nguyễn Hoàng", "IT Specialist", "April", year, janDate,
                    14500000, 9500000, 1000000, 2000000, 500000,
                    950000, 475000, 285000, 190000));

            payslips.add(new Payslip("8", "1", "Nguyễn Hoàng", "IT Specialist", "May", year, janDate,
                    15000000, 10000000, 1000000, 2000000, 500000,
                    1000000, 500000, 300000, 200000));

            payslips.add(new Payslip("9", "1", "Nguyễn Hoàng", "IT Specialist", "June", year, febDate,
                    15500000, 10000000, 1500000, 2000000, 500000,
                    1000000, 500000, 300000, 200000));

            payslips.add(new Payslip("10", "2", "Nguyễn Hoàng", "IT Specialist", "July", year, janDate,
                    14000000, 9000000, 800000, 2000000, 500000,
                    900000, 450000, 270000, 180000));

            payslips.add(new Payslip("11", "1", "Nguyễn Hoàng", "IT Specialist", "August", year, janDate,
                    14500000, 9500000, 1000000, 2000000, 500000,
                    950000, 475000, 285000, 190000));

            payslips.add(new Payslip("12", "1", "Nguyễn Hoàng", "IT Specialist", "September", year, janDate,
                    15000000, 10000000, 1000000, 2000000, 500000,
                    1000000, 500000, 300000, 200000));

            payslips.add(new Payslip("13", "1", "Nguyễn Hoàng", "IT Specialist", "October", year, febDate,
                    15500000, 10000000, 1500000, 2000000, 500000,
                    1000000, 500000, 300000, 200000));

            payslips.add(new Payslip("14", "2", "Nguyễn Hoàng", "IT Specialist", "November", year, janDate,
                    14000000, 9000000, 800000, 2000000, 500000,
                    900000, 450000, 270000, 180000));

            payslips.add(new Payslip("15", "1", "Nguyễn Hoàng", "IT Specialist", "December", year, janDate,
                    14500000, 9500000, 1000000, 2000000, 500000,
                    950000, 475000, 285000, 190000));
        }

        return payslips;
    }
}

