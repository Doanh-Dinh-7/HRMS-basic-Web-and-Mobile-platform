package com.example.hrms_mobile.model;

import java.util.Date;

public class TimeRecord {
    private String id;
    private String employeeId;
    private String employeeName;
    private Date date;
    private Date timeStart;
    private Date timeEnd;
    private String department;
    private String shift;
    private String taskType;
    private String notes;
    private String status; // "On shift", "End of shift"

    public TimeRecord() {
        // Default constructor
    }

    public TimeRecord(String id, String employeeId, String employeeName, Date date,
                      Date timeStart, Date timeEnd, String department,
                      String shift, String taskType, String notes, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.department = department;
        this.shift = shift;
        this.taskType = taskType;
        this.notes = notes;
        this.status = status;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


