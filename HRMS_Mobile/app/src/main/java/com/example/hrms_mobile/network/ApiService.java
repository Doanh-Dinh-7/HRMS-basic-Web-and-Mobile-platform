package com.example.hrms_mobile.network;

import com.example.hrms_mobile.model.Employee;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("employees")
    Call<List<Employee>> getEmployees();
}
