export const BASE_URL =
  import.meta.env.MODE === "development"
    ? import.meta.env.VITE_API_BASE_URL
    : "/api";

export const getTimesheetList = async () => {
  try {
    const response = await fetch(`${BASE_URL}/timesheets`);
    if (!response.ok) {
      throw new Error("Không thể tải danh sách timesheet");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching timesheet list:", error);
    throw error;
  }
};

export const getTimesheetDetail = async (timesheetId) => {
  try {
    const response = await fetch(`${BASE_URL}/timesheets/${timesheetId}`);
    if (!response.ok) {
      throw new Error("Không thể tải thông tin chi tiết timesheet");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching timesheet detail:", error);
    throw error;
  }
};

export const createTimesheet = async (timesheetData) => {
  try {
    console.log("timesheetData", timesheetData);
    const response = await fetch(`${BASE_URL}/timesheets`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(timesheetData),
    });
    if (!response.ok) {
      throw new Error("Không thể tạo timesheet mới");
    }
    return await response.json();
  } catch (error) {
    console.error("Error creating timesheet:", error);
    throw error;
  }
};

export const updateTimesheet = async (timesheetId, timesheetData) => {
  try {
    const response = await fetch(`${BASE_URL}/timesheets/${timesheetId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(timesheetData),
    });
    if (!response.ok) {
      throw new Error("Không thể cập nhật timesheet");
    }
    return await response.json();
  } catch (error) {
    console.error("Error updating timesheet:", error);
    throw error;
  }
};

export const deleteTimesheet = async (timesheetId) => {
  try {
    const response = await fetch(`${BASE_URL}/timesheets/${timesheetId}`, {
      method: "DELETE",
    });
    if (!response.ok) {
      throw new Error("Không thể xóa timesheet");
    }
    return await response.json();
  } catch (error) {
    console.error("Error deleting timesheet:", error);
    throw error;
  }
};
