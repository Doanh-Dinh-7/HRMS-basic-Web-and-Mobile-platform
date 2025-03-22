export const BASE_URL =
  import.meta.env.MODE === "development"
    ? import.meta.env.VITE_API_BASE_URL
    : "/api";

export const getPayslipList = async () => {
  try {
    const response = await fetch(`${BASE_URL}/payslips`);
    if (!response.ok) {
      throw new Error("Không thể tải danh sách phiếu lương");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching payslip list:", error);
    throw error;
  }
};

export const getPayslipDetail = async (payslipId) => {
  try {
    const response = await fetch(`${BASE_URL}/payslips/${payslipId}`);
    if (!response.ok) {
      throw new Error("Không thể tải thông tin chi tiết phiếu lương");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching payslip detail:", error);
    throw error;
  }
};

export const createPayslip = async (payslipData) => {
  try {
    const response = await fetch(`${BASE_URL}/payslips`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payslipData),
    });
    if (!response.ok) {
      throw new Error("Không thể tạo phiếu lương mới");
    }
    return await response.json();
  } catch (error) {
    console.error("Error creating payslip:", error);
    throw error;
  }
};

export const updatePayslip = async (payslipId, payslipData) => {
  try {
    const response = await fetch(`${BASE_URL}/payslips/${payslipId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payslipData),
    });
    if (!response.ok) {
      throw new Error("Không thể cập nhật phiếu lương");
    }
    return await response.json();
  } catch (error) {
    console.error("Error updating payslip:", error);
    throw error;
  }
};

export const deletePayslip = async (payslipId) => {
  try {
    const response = await fetch(`${BASE_URL}/payslips/${payslipId}`, {
      method: "DELETE",
    });
    if (!response.ok) {
      throw new Error("Không thể xóa phiếu lương");
    }
    return await response.json();
  } catch (error) {
    console.error("Error deleting payslip:", error);
    throw error;
  }
};
