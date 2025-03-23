package com.example.hrms_mobile.model;

import java.util.Date;

public class Payslip {
    private String id;
    private String employeeId;
    private String employeeName;
    private String position;
    private String month;
    private int year;
    private Date paymentDate;
    private double grossSalary;
    private double basicSalary;
    private double overtimePay;
    private double bonus;
    private double otherAllowances;
    private double incomeTax;
    private double socialInsurance;
    private double healthInsurance;
    private double otherDeductions;
    private double netSalaryReceived;

    public Payslip() {
        // Default constructor
    }

    public Payslip(String id, String employeeId, String employeeName, String position,
                   String month, int year, Date paymentDate, double grossSalary,
                   double basicSalary, double overtimePay, double bonus,
                   double otherAllowances, double incomeTax, double socialInsurance,
                   double healthInsurance, double otherDeductions) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.position = position;
        this.month = month;
        this.year = year;
        this.paymentDate = paymentDate;
        this.grossSalary = grossSalary;
        this.basicSalary = basicSalary;
        this.overtimePay = overtimePay;
        this.bonus = bonus;
        this.otherAllowances = otherAllowances;
        this.incomeTax = incomeTax;
        this.socialInsurance = socialInsurance;
        this.healthInsurance = healthInsurance;
        this.otherDeductions = otherDeductions;
        this.netSalaryReceived = calculateNetSalary();
    }

    private double calculateNetSalary() {
        double totalEarnings = basicSalary + overtimePay + bonus + otherAllowances;
        double totalDeductions = incomeTax + socialInsurance + healthInsurance + otherDeductions;
        return totalEarnings - totalDeductions;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
        this.netSalaryReceived = calculateNetSalary();
    }

    public double getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(double overtimePay) {
        this.overtimePay = overtimePay;
        this.netSalaryReceived = calculateNetSalary();
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
        this.netSalaryReceived = calculateNetSalary();
    }

    public double getOtherAllowances() {
        return otherAllowances;
    }

    public void setOtherAllowances(double otherAllowances) {
        this.otherAllowances = otherAllowances;
        this.netSalaryReceived = calculateNetSalary();
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
        this.netSalaryReceived = calculateNetSalary();
    }

    public double getSocialInsurance() {
        return socialInsurance;
    }

    public void setSocialInsurance(double socialInsurance) {
        this.socialInsurance = socialInsurance;
        this.netSalaryReceived = calculateNetSalary();
    }

    public double getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(double healthInsurance) {
        this.healthInsurance = healthInsurance;
        this.netSalaryReceived = calculateNetSalary();
    }

    public double getOtherDeductions() {
        return otherDeductions;
    }

    public void setOtherDeductions(double otherDeductions) {
        this.otherDeductions = otherDeductions;
        this.netSalaryReceived = calculateNetSalary();
    }

    public double getNetSalaryReceived() {
        return netSalaryReceived;
    }
}


