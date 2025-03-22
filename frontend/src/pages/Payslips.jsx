import React, { useState, useEffect } from "react";
import {
  Box,
  Heading,
  Flex,
  Select,
  Button,
  useToast,
  Spinner,
} from "@chakra-ui/react";
import { FiPlus } from "react-icons/fi";
import PayslipTable from "../lib/components/Payslip/PayslipTable";
import PayslipDetail from "../lib/components/Payslip/PayslipDetail";
import PayslipFormModal from "../lib/components/Payslip/PayslipFormModal";
import {
  getPayslipList,
  createPayslip,
  updatePayslip,
} from "../lib/controller/payslipController";

const Payslips = () => {
  const [payslips, setPayslips] = useState([]);
  const [filteredPayslips, setFilteredPayslips] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());
  const [selectedMonth, setSelectedMonth] = useState(new Date().getMonth() + 1);
  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);
  const [isFormModalOpen, setIsFormModalOpen] = useState(false);
  const [selectedPayslipId, setSelectedPayslipId] = useState(null);
  const [selectedPayslip, setSelectedPayslip] = useState(null);
  const toast = useToast();

  useEffect(() => {
    const fetchPayslips = async () => {
      try {
        const response = await getPayslipList();
        setPayslips(response);
        filterPayslips(response, selectedYear, selectedMonth);
      } catch (error) {
        console.error("Error fetching payslips:", error);
        toast({
          title: "Error",
          description: "Cannot load payslip list",
          status: "error",
          duration: 3000,
          isClosable: true,
        });
      } finally {
        setLoading(false);
      }
    };
    fetchPayslips();
  }, [selectedYear, selectedMonth, toast]);

  const filterPayslips = (payslipsList, year, month) => {
    let filtered = payslipsList;
    if (year !== "all") {
      filtered = filtered.filter((payslip) => payslip.year === year);
    }
    if (month !== "all") {
      filtered = filtered.filter((payslip) => payslip.month === month);
    }
    setFilteredPayslips(filtered);
  };

  const handleViewPayslip = (payslipId) => {
    setSelectedPayslipId(payslipId);
    setIsDetailModalOpen(true);
  };

  const handleCloseDetailModal = () => {
    setIsDetailModalOpen(false);
    setSelectedPayslipId(null);
  };

  const handleAddPayslip = () => {
    setSelectedPayslip(null);
    setIsFormModalOpen(true);
  };

  const handleEditPayslip = (payslip) => {
    setSelectedPayslip(payslip);
    setIsFormModalOpen(true);
  };

  const handleCloseFormModal = () => {
    setIsFormModalOpen(false);
    setSelectedPayslip(null);
  };

  const handleSubmitPayslip = async (formData) => {
    try {
      if (selectedPayslip) {
        await updatePayslip(selectedPayslip.payslipID, formData);
      } else {
        await createPayslip(formData);
      }
      handleCloseFormModal();
      window.location.reload();
    } catch (error) {
      console.error("Error submitting payslip:", error);
      throw error;
    }
  };

  const handleYearChange = (event) => {
    const value = event.target.value;
    const year = value === "all" ? value : parseInt(value);
    setSelectedYear(year);
    filterPayslips(payslips, year, selectedMonth);
  };

  const handleMonthChange = (event) => {
    const value = event.target.value;
    const month = value === "all" ? value : parseInt(value);
    setSelectedMonth(month);
    filterPayslips(payslips, selectedYear, month);
  };

  if (loading) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        minH="400px"
      >
        <Spinner size="xl" />
      </Box>
    );
  }

  return (
    <Box>
      <Flex justify="space-between" align="center" mb={6}>
        <Heading size="lg">Payroll Management</Heading>
        <Button
          leftIcon={<FiPlus />}
          colorScheme="black"
          bg="black"
          onClick={handleAddPayslip}
        >
          New Payslip
        </Button>
      </Flex>

      <Flex mb={6} gap={4} align="center">
        <Select
          placeholder="Select year"
          maxW="180px"
          value={selectedYear || "all"}
          onChange={handleYearChange}
        >
          <option value="all">All years</option>
          {Array.from(
            { length: 5 },
            (_, i) => new Date().getFullYear() - i
          ).map((year) => (
            <option key={year} value={year}>
              {year}
            </option>
          ))}
        </Select>

        <Select
          placeholder="Select month"
          maxW="180px"
          value={selectedMonth || "all"}
          onChange={handleMonthChange}
        >
          <option value="all">All months</option>
          {Array.from({ length: 12 }, (_, i) => i + 1).map((month) => (
            <option key={month} value={month}>
              Month {month}
            </option>
          ))}
        </Select>
      </Flex>

      <PayslipTable
        payslips={filteredPayslips}
        onViewPayslip={handleViewPayslip}
        onEditPayslip={handleEditPayslip}
      />

      <PayslipDetail
        isOpen={isDetailModalOpen}
        onClose={handleCloseDetailModal}
        payslipId={selectedPayslipId}
      />

      <PayslipFormModal
        isOpen={isFormModalOpen}
        onClose={handleCloseFormModal}
        payslip={selectedPayslip}
        onSubmit={handleSubmitPayslip}
      />
    </Box>
  );
};

export default Payslips;
