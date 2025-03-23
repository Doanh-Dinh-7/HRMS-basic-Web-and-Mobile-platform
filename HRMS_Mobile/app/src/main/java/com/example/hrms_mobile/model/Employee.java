package com.example.hrms_mobile.model;

import java.util.Date;

public class Employee {
    private String employeeID;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String position;
    private String department;
    private String manager;
    private String avatar;

    // Thêm các trường mới
    private String gender;
    private Date dateOfBirth;
    private String nationality;
    private String idNumber;
    private String healthInsuranceNumber;
    private String address;
    private String country;
    private String city;
    private String state;
    private String postalCode;
    private String bankName;
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

    public Employee(String employeeID, String fullName, String email, String phoneNumber, String position, String department, String manager, String avatar) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.email = email != null ? email : ""; // Đảm bảo không null
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.department = department;
        this.manager = manager;
        this.avatar = avatar != null ? avatar : ""; // Đảm bảo không null
        this.active = true;
    }

    // Constructor đầy đủ với các trường mới
    public Employee(String employeeID, String fullName, String email, String phoneNumber, String position,
                    String department, String manager, String avatar, String gender, Date dateOfBirth,
                    String nationality, String idNumber, String healthInsuranceNumber, String address,
                    String country, String city, String state, String postalCode, String bankName,
                    String accountName, String accountNumber, String branch, String swift, String iban,
                    String emergencyContactName, String emergencyContactRelationship, String emergencyContactPhone,
                    boolean active) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.department = department;
        this.manager = manager;
        this.avatar = avatar;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.idNumber = idNumber;
        this.healthInsuranceNumber = healthInsuranceNumber;
        this.address = address;
        this.country = country;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.branch = branch;
        this.swift = swift;
        this.iban = iban;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactRelationship = emergencyContactRelationship;
        this.emergencyContactPhone = emergencyContactPhone;
        this.active = active;
    }

    // Getters and Setters
    public String getId() {
        return employeeID;
    }

    public void setId(String employeeID) {
        this.employeeID = employeeID;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }

    public void setHealthInsuranceNumber(String healthInsuranceNumber) {
        this.healthInsuranceNumber = healthInsuranceNumber;
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
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
}

