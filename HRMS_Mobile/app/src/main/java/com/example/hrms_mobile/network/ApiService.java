package com.example.hrms_mobile.network;

import com.example.hrms_mobile.model.Employee;
import com.example.hrms_mobile.model.ApiResponse;
import com.example.hrms_mobile.model.TimeRecord;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("employees")
    Call<List<Employee>> getEmployees();

    @GET("employees/{id}")
    Call<Employee> getEmployeeDetail(@Path("id") String employeeId);

    @POST("employees")
    Call<ApiResponse> createEmployee(@Body Employee employee);

    @PUT("employees/{id}")
    Call<ApiResponse> updateEmployee(@Path("id") String employeeId, @Body Employee employee);

    @DELETE("employees/{id}")
    Call<ApiResponse> deleteEmployee(@Path("id") String employeeId);

    @GET("mobile-timekeeping/EMP005")
    Call<List<TimeRecord>> getMobileTimekeeping();

    @POST("mobile-timekeeping")
    Call<ApiResponse> createMobileTimekeeping(@Body TimeRecord timeRecord);

    @PUT("mobile-timekeeping/checkout/{check_id}")
    Call<ApiResponse> updateCheckoutAndCalculateHours(
            @Path("check_id") String checkId,
            @Body TimeRecord timeRecord);

    @DELETE("mobile-timekeeping/{id}")
    Call<ApiResponse> deleteMobileTimekeeping(@Path("id") String id);
}
