import React, { useState, useEffect } from "react";
import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalCloseButton,
  FormControl,
  FormLabel,
  Input,
  Button,
  VStack,
  HStack,
  Box,
  IconButton,
  useToast,
} from "@chakra-ui/react";
import { FiPlus, FiTrash } from "react-icons/fi";
import {
  createPayslip,
  getPayslipDetail,
  updatePayslip,
} from "../../controller/payslipController";

const PayslipFormModal = ({ isOpen, onClose, payslip, onSubmit }) => {
  const [formData, setFormData] = useState({
    employeeID: "",
    fullName: "",
    position: "",
    month: new Date().getMonth() + 1,
    year: new Date().getFullYear(),
    workday: 0,
    basicSalary: 0,
    netSalary: 0,
    allowances: [],
    deductions: [],
  });
  const toast = useToast();

  useEffect(() => {
    const fetchPayslipDetail = async () => {
      if (payslip) {
        try {
          const payslipDetail = await getPayslipDetail(payslip.payslipID);
          setFormData({
            employeeID: payslipDetail.employeeID || "",
            fullName: payslipDetail.fullName || "",
            position: payslipDetail.position || "",
            month: payslipDetail.month || new Date().getMonth() + 1,
            year: payslipDetail.year || new Date().getFullYear(),
            workday: payslipDetail.workday || 0,
            basicSalary: payslipDetail.basicSalary || 0,
            netSalary: payslipDetail.netSalary || 0,
            allowances: payslipDetail.allowances || [],
            deductions: payslipDetail.deductions || [],
          });
        } catch (error) {
          console.error("Error fetching payslip detail:", error);
          toast({
            title: "Error",
            description: "Cannot fetch payslip detail",
            status: "error",
            duration: 3000,
            isClosable: true,
          });
        }
      } else {
        setFormData({
          employeeID: "",
          fullName: "",
          position: "",
          month: new Date().getMonth() + 1,
          year: new Date().getFullYear(),
          workday: 0,
          basicSalary: 0,
          netSalary: 0,
          allowances: [],
          deductions: [],
        });
      }
    };
    fetchPayslipDetail();
  }, [payslip, toast]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleAddAllowance = () => {
    setFormData((prev) => ({
      ...prev,
      allowances: [...prev.allowances, { allowenceType: "", amount: 0 }],
    }));
  };

  const handleAddDeduction = () => {
    setFormData((prev) => ({
      ...prev,
      deductions: [...prev.deductions, { deductionType: "", amount: 0 }],
    }));
  };

  const handleAllowanceChange = (index, field, value) => {
    setFormData((prev) => {
      const newAllowances = [...prev.allowances];
      newAllowances[index] = {
        ...newAllowances[index],
        [field]: value,
      };
      return { ...prev, allowances: newAllowances };
    });
  };

  const handleDeductionChange = (index, field, value) => {
    setFormData((prev) => {
      const newDeductions = [...prev.deductions];
      newDeductions[index] = {
        ...newDeductions[index],
        [field]: value,
      };
      return { ...prev, deductions: newDeductions };
    });
  };

  const handleRemoveAllowance = (index) => {
    setFormData((prev) => ({
      ...prev,
      allowances: prev.allowances.filter((_, i) => i !== index),
    }));
  };

  const handleRemoveDeduction = (index) => {
    setFormData((prev) => ({
      ...prev,
      deductions: prev.deductions.filter((_, i) => i !== index),
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await onSubmit(formData);
      onClose();
      toast({
        title: "Success",
        description: payslip
          ? "Cập nhật phiếu lương thành công"
          : "Tạo phiếu lương mới thành công",
        status: "success",
        duration: 3000,
        isClosable: true,
      });
    } catch (error) {
      console.error("Error submitting form:", error);
      toast({
        title: "Lỗi",
        description: payslip
          ? "Không thể cập nhật phiếu lương"
          : "Không thể tạo phiếu lương mới",
        status: "error",
        duration: 3000,
        isClosable: true,
      });
    }
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose} size="xl">
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>
          {payslip ? "Chỉnh sửa phiếu lương" : "Tạo phiếu lương mới"}
        </ModalHeader>
        <ModalCloseButton />
        <ModalBody pb={6}>
          <form onSubmit={handleSubmit}>
            <VStack spacing={4}>
              <FormControl isRequired>
                <FormLabel>Mã nhân viên</FormLabel>
                <Input
                  name="employeeID"
                  value={formData.employeeID}
                  onChange={handleChange}
                />
              </FormControl>

              <HStack spacing={4} width="100%">
                <FormControl isRequired>
                  <FormLabel>Tháng</FormLabel>
                  <Input
                    type="number"
                    name="month"
                    value={formData.month}
                    onChange={handleChange}
                    min={1}
                    max={12}
                  />
                </FormControl>

                <FormControl isRequired>
                  <FormLabel>Năm</FormLabel>
                  <Input
                    type="number"
                    name="year"
                    value={formData.year}
                    onChange={handleChange}
                  />
                </FormControl>
              </HStack>

              <FormControl isRequired>
                <FormLabel>Lương cơ bản</FormLabel>
                <Input
                  type="number"
                  name="basicSalary"
                  value={formData.basicSalary}
                  onChange={handleChange}
                />
              </FormControl>

              <FormControl isRequired>
                <FormLabel>Lương thực lĩnh</FormLabel>
                <Input
                  type="number"
                  name="netSalary"
                  value={formData.netSalary}
                  onChange={handleChange}
                />
              </FormControl>

              <Box width="100%">
                <HStack justify="space-between" mb={2}>
                  <FormLabel>Phụ cấp</FormLabel>
                  <IconButton
                    icon={<FiPlus />}
                    size="sm"
                    onClick={handleAddAllowance}
                  />
                </HStack>
                <VStack spacing={2}>
                  {formData.allowances.map((allowance, index) => (
                    <HStack key={index} width="100%">
                      <Input
                        placeholder="Loại phụ cấp"
                        value={allowance.allowenceType}
                        onChange={(e) =>
                          handleAllowanceChange(
                            index,
                            "allowenceType",
                            e.target.value
                          )
                        }
                      />
                      <Input
                        type="number"
                        placeholder="Số tiền"
                        value={allowance.amount}
                        onChange={(e) =>
                          handleAllowanceChange(
                            index,
                            "amount",
                            parseFloat(e.target.value)
                          )
                        }
                      />
                      <IconButton
                        icon={<FiTrash />}
                        colorScheme="red"
                        size="sm"
                        onClick={() => handleRemoveAllowance(index)}
                      />
                    </HStack>
                  ))}
                </VStack>
              </Box>

              <Box width="100%">
                <HStack justify="space-between" mb={2}>
                  <FormLabel>Khấu trừ</FormLabel>
                  <IconButton
                    icon={<FiPlus />}
                    size="sm"
                    onClick={handleAddDeduction}
                  />
                </HStack>
                <VStack spacing={2}>
                  {formData.deductions.map((deduction, index) => (
                    <HStack key={index} width="100%">
                      <Input
                        placeholder="Loại khấu trừ"
                        value={deduction.deductionType}
                        onChange={(e) =>
                          handleDeductionChange(
                            index,
                            "deductionType",
                            e.target.value
                          )
                        }
                      />
                      <Input
                        type="number"
                        placeholder="Số tiền"
                        value={deduction.amount}
                        onChange={(e) =>
                          handleDeductionChange(
                            index,
                            "amount",
                            parseFloat(e.target.value)
                          )
                        }
                      />
                      <IconButton
                        icon={<FiTrash />}
                        colorScheme="red"
                        size="sm"
                        onClick={() => handleRemoveDeduction(index)}
                      />
                    </HStack>
                  ))}
                </VStack>
              </Box>

              <Button type="submit" colorScheme="blue" width="100%">
                {payslip ? "Cập nhật" : "Tạo mới"}
              </Button>
            </VStack>
          </form>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default PayslipFormModal;
