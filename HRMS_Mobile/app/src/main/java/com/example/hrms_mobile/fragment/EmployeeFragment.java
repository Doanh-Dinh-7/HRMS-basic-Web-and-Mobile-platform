package com.example.hrms_mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_mobile.R;
import com.example.hrms_mobile.activity.AddEmployeeActivity;
import com.example.hrms_mobile.adapter.EmployeeAdapter;
import com.example.hrms_mobile.model.Employee;
import com.example.hrms_mobile.model.ApiResponse;
import com.example.hrms_mobile.network.ApiService;
import com.example.hrms_mobile.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeFragment extends Fragment implements EmployeeAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private SearchView searchView;
    private FloatingActionButton fabAdd;
    private TextView tvEmployeeCount;

    private List<Employee> employeeList;
    private EmployeeAdapter employeeAdapter;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        searchView = view.findViewById(R.id.search_view);
        fabAdd = view.findViewById(R.id.fab_add);
        tvEmployeeCount = view.findViewById(R.id.tv_employee_count);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize sample data
        // employeeList = getSampleEmployees();
        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(getContext(), employeeList, this);
        recyclerView.setAdapter(employeeAdapter);

        // Fetch employees from the API
        apiService = RetrofitClient.getClient().create(ApiService.class);
        loadEmployees();

        // Set employee count
        updateEmployeeCount();

        // Setup search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Khi người dùng nhấn "Enter" hoặc "Search"
                System.out.println("Query submitted: " + query); // In giá trị query ra log
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("New text entered: " + newText); // In giá trị newText ra log
                employeeAdapter.getFilter().filter(newText);
                updateEmployeeCount();
                return true;
            }
        });

        // Setup FAB for adding new employee
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddEmployeeActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void updateEmployeeCount() {
        int totalEmployees = employeeAdapter.getFilteredEmployeeCount();
        tvEmployeeCount.setText(String.format("Showing %d of %d employees", totalEmployees, employeeList.size()));
    }

    // private List<Employee> getSampleEmployees() {
    // // Sample data for demonstration
    // List<Employee> employees = new ArrayList<>();
    // employees.add(new Employee("1", "Nguyễn Văn An", "an@example.com",
    // "0987654321", "IT Specialist", "IT", "John Smith", ""));
    // employees.add(new Employee("2", "Trần Thị Bình", "binh@example.com",
    // "0876543210", "UX Designer", "Design", "John Smith", ""));
    // employees.add(new Employee("3", "Lê Văn Cường", "cuong@example.com",
    // "0987654320", "Developer", "IT", "John Smith", ""));
    // employees.add(new Employee("4", "Phạm Thị Dung", "dung@example.com",
    // "0912345678", "HR Manager", "HR", "John Smith", ""));
    // employees.add(new Employee("5", "Trần Văn Quản", "quan@example.com",
    // "0861234567", "CEO", "Executive", "", ""));
    //
    // // Add 10 more sample employees with Vietnamese names but English positions
    // employees.add(new Employee("6", "Hoàng Thị Hương", "huong@example.com",
    // "0987654311", "Marketing Manager", "Marketing", "John Smith", ""));
    // employees.add(new Employee("7", "Vũ Đình Minh", "minh@example.com",
    // "0987654312", "Data Analyst", "IT", "Lê Văn Cường", ""));
    // employees.add(new Employee("8", "Nguyễn Thị Lan", "lan@example.com",
    // "0987654313", "Graphic Designer", "Design", "Trần Thị Bình", ""));
    // employees.add(new Employee("9", "Đỗ Văn Tùng", "tung@example.com",
    // "0987654314", "Financial Analyst", "Finance", "John Smith", ""));
    // employees.add(new Employee("10", "Lê Thị Mai", "mai@example.com",
    // "0987654315", "Customer Support", "Support", "Phạm Thị Dung", ""));
    // employees.add(new Employee("11", "Nguyễn Văn Hùng", "hung@example.com",
    // "0987654316", "Sales Representative", "Sales", "John Smith", ""));
    // employees.add(new Employee("12", "Trần Thị Thảo", "thao@example.com",
    // "0987654317", "Product Manager", "Product", "John Smith", ""));
    // employees.add(new Employee("13", "Phạm Văn Đức", "duc@example.com",
    // "0987654318", "QA Engineer", "IT", "Lê Văn Cường", ""));
    // employees.add(new Employee("14", "Nguyễn Thị Hà", "ha@example.com",
    // "0987654319", "Content Editor", "Marketing", "Hoàng Thị Hương", ""));
    // employees.add(new Employee("15", "Lê Văn Thành", "thanh@example.com",
    // "0987654320", "System Administrator", "IT", "Lê Văn Cường", ""));
    //
    // return employees;
    // }
    private void loadEmployees() {
        apiService.getEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    employeeList.clear();
                    employeeList.addAll(response.body());
                    employeeAdapter.updateData(employeeList);
                    employeeAdapter.notifyDataSetChanged();

                    // Update employee count
                    updateEmployeeCount();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tải danh sách nhân viên", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh data when coming back to this fragment (after adding/editing an
        // employee)
        // employeeList = getSampleEmployees();
        loadEmployees();
        employeeAdapter.updateData(employeeList);
        updateEmployeeCount();
    }

    @Override
    public void onItemClick(Employee employee) {
        apiService.getEmployeeDetail(employee.getEmployeeId()).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Hiển thị dialog chi tiết nhân viên
                    showEmployeeDetailDialog(response.body());
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tải chi tiết nhân viên", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEditClick(Employee employee) {
        // Hiển thị dialog chỉnh sửa nhân viên
        showEditEmployeeDialog(employee);
    }

    @Override
    public void onDeleteClick(Employee employee) {
        // Hiển thị dialog xác nhận xóa
        showDeleteConfirmationDialog(employee);
    }

    private void showEmployeeDetailDialog(Employee employee) {
        // TODO: Implement employee detail dialog
    }

    private void showEditEmployeeDialog(Employee employee) {
        // TODO: Implement edit employee dialog
    }

    private void showDeleteConfirmationDialog(Employee employee) {
        // TODO: Implement delete confirmation dialog
    }

    private void deleteEmployee(Employee employee) {
        apiService.deleteEmployee(employee.getEmployeeId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(getContext(), "Xóa nhân viên thành công", Toast.LENGTH_SHORT).show();
                    loadEmployees();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi xóa nhân viên", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
