package com.example.hrms_mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_mobile.R;
import com.example.hrms_mobile.activity.TimeRecordFormActivity;
import com.example.hrms_mobile.adapter.TimeRecordAdapter;
import com.example.hrms_mobile.model.TimeRecord;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeKeepingFragment extends Fragment {
    private RecyclerView recyclerView;
    private TimeRecordAdapter adapter;
    private FloatingActionButton fabAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timekeeping, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        fabAdd = view.findViewById(R.id.fab_add);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize sample data
        List<TimeRecord> timeRecordList = getSampleTimeRecords();
        adapter = new TimeRecordAdapter(getContext(), timeRecordList);
        recyclerView.setAdapter(adapter);

        // Setup FAB for adding new time record
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TimeRecordFormActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private List<TimeRecord> getSampleTimeRecords() {
        // Sample data for demonstration
        List<TimeRecord> records = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date twoDaysAgo = cal.getTime();

        records.add(new TimeRecord("1", "1", "Nguyễn Hoàng", today, today, null, "IT", "Morning", "Regular", "", "On shift"));

        return records;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh data when coming back to this fragment
        adapter.updateData(getSampleTimeRecords());
    }
}

