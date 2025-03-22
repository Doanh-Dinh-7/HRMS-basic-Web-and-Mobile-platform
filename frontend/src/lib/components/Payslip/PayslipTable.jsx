import React from "react";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  IconButton,
  HStack,
  useToast,
} from "@chakra-ui/react";
import { FiEdit2, FiTrash2, FiEye } from "react-icons/fi";
import { deletePayslip } from "../../controller/payslipController";

const PayslipTable = ({ payslips, onViewPayslip, onEditPayslip }) => {
  const toast = useToast();

  const handleDelete = async (payslipId) => {
    if (window.confirm("Are you sure you want to delete this payslip?")) {
      try {
        await deletePayslip(payslipId);
        toast({
          title: "Success",
          description: "Payslip deleted successfully",
          status: "success",
          duration: 3000,
          isClosable: true,
        });
        window.location.reload();
      } catch (error) {
        console.error("Error deleting payslip:", error);
        toast({
          title: "Error",
          description: "Cannot delete payslip",
          status: "error",
          duration: 3000,
          isClosable: true,
        });
      }
    }
  };

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(amount);
  };

  return (
    <Table variant="simple">
      <Thead>
        <Tr>
          <Th>Payslip ID</Th>
          <Th>Employee ID</Th>
          <Th>Full Name</Th>
          <Th>Position</Th>
          <Th>Workday</Th>
          <Th>Basic Salary</Th>
          <Th>Total Salary</Th>
          <Th>Actions</Th>
        </Tr>
      </Thead>
      <Tbody>
        {payslips.map((payslip) => (
          <Tr key={payslip.payslipID}>
            <Td>{payslip.payslipID}</Td>
            <Td>{payslip.employeeID}</Td>
            <Td>{payslip.fullName}</Td>
            <Td>{payslip.position}</Td>
            <Td>{payslip.workday}</Td>
            <Td>{formatCurrency(payslip.basicSalary)}</Td>
            <Td>{formatCurrency(payslip.basicSalary + payslip.netSalary)}</Td>
            <Td>
              <HStack spacing={2}>
                <IconButton
                  icon={<FiEdit2 />}
                  size="sm"
                  variant="ghost"
                  colorScheme="yellow"
                  onClick={() => onEditPayslip(payslip)}
                  aria-label="Edit"
                />
                <IconButton
                  icon={<FiTrash2 />}
                  size="sm"
                  variant="ghost"
                  colorScheme="red"
                  onClick={() => handleDelete(payslip.payslipID)}
                  aria-label="Delete"
                />
                <IconButton
                  icon={<FiEye />}
                  size="sm"
                  variant="ghost"
                  colorScheme="blue"
                  onClick={() => onViewPayslip(payslip.payslipID)}
                  aria-label="View"
                />
              </HStack>
            </Td>
          </Tr>
        ))}
      </Tbody>
    </Table>
  );
};

export default PayslipTable;
