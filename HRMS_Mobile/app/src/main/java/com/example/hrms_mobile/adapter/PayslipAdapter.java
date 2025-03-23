package com.example.hrms_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_mobile.R;
import com.example.hrms_mobile.activity.PayslipDetailsActivity;
import com.example.hrms_mobile.model.Payslip;
import com.example.hrms_mobile.util.CurrencyFormatter;

import java.util.List;

public class PayslipAdapter extends RecyclerView.Adapter<PayslipAdapter.PayslipViewHolder> {
    private List<Payslip> payslipList;
    private Context context;

    public PayslipAdapter(Context context, List<Payslip> payslipList) {
        this.context = context;
        this.payslipList = payslipList;
    }

    @NonNull
    @Override
    public PayslipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payslip, parent, false);
        return new PayslipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayslipViewHolder holder, int position) {
        Payslip payslip = payslipList.get(position);

        holder.tvEmployeeName.setText(payslip.getEmployeeName());
        holder.tvPosition.setText(payslip.getPosition());
        holder.tvMonthYear.setText(payslip.getMonth() + " " + payslip.getYear());
        holder.tvAmount.setText(CurrencyFormatter.format(payslip.getNetSalaryReceived()));

        // Set click listener to open payslip details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PayslipDetailsActivity.class);
            intent.putExtra("PAYSLIP_ID", payslip.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return payslipList.size();
    }

    public class PayslipViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmployeeName, tvPosition, tvMonthYear, tvAmount;

        public PayslipViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmployeeName = itemView.findViewById(R.id.tv_employee_name);
            tvPosition = itemView.findViewById(R.id.tv_position);
            tvMonthYear = itemView.findViewById(R.id.tv_month_year);
            tvAmount = itemView.findViewById(R.id.tv_amount);
        }
    }

    public void updateData(List<Payslip> newPayslipList) {
        this.payslipList = newPayslipList;
        notifyDataSetChanged();
    }
}

