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
import com.example.hrms_mobile.activity.TimeRecordDetailsActivity;
import com.example.hrms_mobile.model.TimeRecord;
import com.example.hrms_mobile.util.DateTimeUtil;

import java.util.List;

public class TimeRecordAdapter extends RecyclerView.Adapter<TimeRecordAdapter.TimeRecordViewHolder> {
    private List<TimeRecord> timeRecordList;
    private Context context;

    public TimeRecordAdapter(Context context, List<TimeRecord> timeRecordList) {
        this.context = context;
        this.timeRecordList = timeRecordList;
    }

    @NonNull
    @Override
    public TimeRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_record, parent, false);
        return new TimeRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeRecordViewHolder holder, int position) {
        TimeRecord timeRecord = timeRecordList.get(position);

        holder.tvEmployeeName.setText(timeRecord.getEmployeeName());
        holder.tvDate.setText(DateTimeUtil.formatDate(timeRecord.getDate()));
        holder.tvStatus.setText(timeRecord.getStatus());

        // Set tag color based on status
        if ("On shift".equals(timeRecord.getStatus())) {
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_active);
        } else {
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_inactive);
        }

        // Set click listener to open time record details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TimeRecordDetailsActivity.class);
            intent.putExtra("TIME_RECORD_ID", timeRecord.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return timeRecordList.size();
    }

    public class TimeRecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmployeeName, tvDate, tvStatus;

        public TimeRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmployeeName = itemView.findViewById(R.id.tv_employee_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }

    public void updateData(List<TimeRecord> newTimeRecordList) {
        this.timeRecordList = newTimeRecordList;
        notifyDataSetChanged();
    }
}


