package com.example.hrms_mobile.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hrms_mobile.R;
import com.example.hrms_mobile.activity.EmployeeDetailsActivity;
import com.example.hrms_mobile.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> implements Filterable {
    private List<Employee> employeeList;
    private List<Employee> employeeListFull;
    private Context context;

    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
        this.employeeListFull = new ArrayList<>(employeeList);
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.tvName.setText(employee.getFullName());
        holder.tvEmail.setText(employee.getEmail());
        holder.tvPosition.setText(employee.getPosition());

        // Load avatar image
        if (employee.getAvatar() != null && !employee.getAvatar().isEmpty()) {
            Glide.with(context)
                    .load(employee.getAvatar())
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(holder.ivAvatar);
        } else {
            holder.ivAvatar.setImageResource(R.drawable.ic_person);
        }

        // Set click listener to open employee details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EmployeeDetailsActivity.class);
            intent.putExtra("EMPLOYEE_ID", employee.getId());
            context.startActivity(intent);
        });

        // Set click listener for delete button
        holder.ivDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(employee, position);
        });
    }

    private void showDeleteConfirmationDialog(Employee employee, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên " + employee.getFullName() + "?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            // Xóa nhân viên khỏi danh sách
            employeeList.remove(position);
            employeeListFull.remove(employee);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, employeeList.size());
            Toast.makeText(context, "Đã xóa nhân viên", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    private Filter employeeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Employee> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(employeeListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                System.out.println("Filter pattern: " + filterPattern); // In giá trị filterPattern ra log
                System.out.println("EmployeeListFull size: " + employeeListFull.size());
                for (Employee employee : employeeListFull) {
                    if ((employee.getFullName() != null && employee.getFullName().toLowerCase().contains(filterPattern)) ||
                            (employee.getEmail() != null && employee.getEmail().toLowerCase().contains(filterPattern)) ||
                            (employee.getPosition() != null && employee.getPosition().toLowerCase().contains(filterPattern))) {
                        filteredList.add(employee);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            employeeList.clear();
            employeeList.addAll((List) results.values);
            notifyDataSetChanged();

            if (employeeList.isEmpty()) {
                Toast.makeText(context, "No matching employees found", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public Filter getFilter() {
        return employeeFilter;
    }

    public int getFilteredEmployeeCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar, ivDelete;
        TextView tvName, tvEmail, tvPosition;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvPosition = itemView.findViewById(R.id.tv_position);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }

    public void updateData(List<Employee> newEmployeeList) {
        this.employeeList = newEmployeeList;
        this.employeeListFull = new ArrayList<>(newEmployeeList);
        notifyDataSetChanged();
    }
}

