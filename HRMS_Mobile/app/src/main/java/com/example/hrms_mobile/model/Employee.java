package com.example.hrms_mobile.model;

import java.util.Date;
import com.google.gson.annotations.SerializedName;

public class Employee {
    @SerializedName("employeeID")
    private String employeeId;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("position")
    private String position;

    @SerializedName("departmentName")
    private String departmentName;

    @SerializedName("gender")
    private String gender;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("nationalIDNumber")
    private String nationalIDNumber;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("healthInsurance")
    private String healthInsurance;

    @SerializedName("address")
    private String address;

    @SerializedName("bankAccountNumber")
    private String bankAccountNumber;

    @SerializedName("bankName")
    private String bankName;

    @SerializedName("status")
    private String status;

    // Thêm các trường mới
    private String email;
    private String manager;
    private String avatar;
    private String idNumber;
    private String healthInsuranceNumber;
    private String country;
    private String city;
    private String state;
    private String postalCode;
    private String accountName;
    private String accountNumber;
    private String branch;
    private String swift;
    private String iban;
    private String emergencyContactName;
    private String emergencyContactRelationship;
    private String emergencyContactPhone;
    private boolean active;

    public Employee() {
        // Default constructor
    }

    public Employee(String employeeID, String fullName, String email, String phoneNumber, String position,
            String department, String manager, String avatar) {
        this.employeeId = employeeID;
        this.fullName = fullName;
        this.email = email != null ? email : ""; // Đảm bảo không null
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.departmentName = department;
        this.manager = manager;
        this.avatar = avatar != null ? avatar : ""; // Đảm bảo không null
        this.active = true;
    }

    // Constructor đầy đủ với các trường mới
    public Employee(String employeeID, String fullName, String email, String phoneNumber, String position,
            String department, String manager, String avatar, String gender, String dateOfBirth,
            String nationality, String idNumber, String healthInsuranceNumber, String address,
            String country, String city, String state, String postalCode, String bankName,
            String accountName, String accountNumber, String branch, String swift, String iban,
            String emergencyContactName, String emergencyContactRelationship, String emergencyContactPhone,
            boolean active) {
        this.employeeId = employeeID;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.departmentName = department;
        this.manager = manager;
        this.avatar = avatar;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.nationalIDNumber = idNumber;
        this.healthInsurance = healthInsuranceNumber;
        this.address = address;
        this.country = country;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.bankName = bankName;
        this.accountName = accountName;
        this.bankAccountNumber = accountNumber;
        this.branch = branch;
        this.swift = swift;
        this.iban = iban;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactRelationship = emergencyContactRelationship;
        this.emergencyContactPhone = emergencyContactPhone;
        this.active = active;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeID) {
        this.employeeId = employeeID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String department) {
        this.departmentName = department;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalIDNumber() {
        return nationalIDNumber;
    }

    public void setNationalIDNumber(String idNumber) {
        this.nationalIDNumber = idNumber;
    }

    public String getHealthInsuranceNumber() {
        return healthInsurance;
    }

    public void setHealthInsuranceNumber(String healthInsuranceNumber) {
        this.healthInsurance = healthInsuranceNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return bankAccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.bankAccountNumber = accountNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
