import React, { useState, useEffect } from "react";
import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalCloseButton,
  VStack,
  HStack,
  Box,
  Text,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  Divider,
  useToast,
  Spinner,
} from "@chakra-ui/react";
import { getPayslipDetail } from "../../controller/payslipController";

const PayslipDetail = ({ isOpen, onClose, payslipId }) => {
  const [payslipDetail, setPayslipDetail] = useState(null);
  const [loading, setLoading] = useState(true);
  const toast = useToast();

  useEffect(() => {
    const fetchPayslipDetail = async () => {
      if (payslipId) {
        try {
          setLoading(true);
          const response = await getPayslipDetail(payslipId);
          setPayslipDetail(response);
        } catch (error) {
          console.error("Error fetching payslip detail:", error);
          toast({
            title: "Error",
            description: "Cannot load payslip detail",
            status: "error",
            duration: 3000,
            isClosable: true,
          });
        } finally {
          setLoading(false);
        }
      }
    };

    fetchPayslipDetail();
  }, [payslipId, toast]);

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(amount);
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose} size="6xl">
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Payslip Detail</ModalHeader>
        <ModalCloseButton />
        <ModalBody pb={6}>
          {loading ? (
            <Box display="flex" justifyContent="center" p={4}>
              <Spinner size="xl" />
            </Box>
          ) : payslipDetail ? (
            <VStack spacing={6} align="stretch">
              {/* Thông tin tổng quan */}
              <Box p={4} bg="gray.50" borderRadius="md">
                <VStack align="stretch" spacing={2}>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Payslip ID:</Text>
                    <Text>{payslipDetail.payslipID}</Text>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Employee ID:</Text>
                    <Text>{payslipDetail.employeeID}</Text>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Full Name:</Text>
                    <Text>{payslipDetail.fullName}</Text>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Position:</Text>
                    <Text>{payslipDetail.position}</Text>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Time:</Text>
                    <Text>
                      Month {payslipDetail.month}/{payslipDetail.year}
                    </Text>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Workday:</Text>
                    <Text>{payslipDetail.workday} days</Text>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Basic Salary:</Text>
                    <Text>{formatCurrency(payslipDetail.basicSalary)}</Text>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Net Salary:</Text>
                    <Text>{formatCurrency(payslipDetail.netSalary)}</Text>
                  </HStack>
                </VStack>
              </Box>

              <Divider />

              {/* Chi tiết phụ cấp và khấu trừ */}
              <HStack align="start" spacing={8}>
                {/* Bảng phụ cấp - bên trái Có tổng số tiền phụ cấp ở dưới cùng*/}
                <Box flex={1}>
                  <Text fontSize="lg" fontWeight="bold" mb={4}>
                    Allowances
                  </Text>
                  <Table variant="simple">
                    <Thead>
                      <Tr>
                        <Th>Allowance Type</Th>
                        <Th isNumeric>Amount</Th>
                      </Tr>
                    </Thead>
                    <Tbody>
                      {payslipDetail.allowances.map((allowance) => (
                        <Tr key={allowance.allowenceID}>
                          <Td>{allowance.allowenceType}</Td>
                          <Td isNumeric>{formatCurrency(allowance.amount)}</Td>
                        </Tr>
                      ))}
                      <Tr>
                        <Td>Total</Td>
                        <Td isNumeric fontWeight="bold" color="green">
                          {formatCurrency(
                            payslipDetail.allowances.reduce(
                              (sum, allowance) => sum + allowance.amount,
                              0
                            )
                          )}
                        </Td>
                      </Tr>
                    </Tbody>
                  </Table>
                </Box>

                {/* Bảng khấu trừ - bên phải */}
                <Box flex={1}>
                  <Text fontSize="lg" fontWeight="bold" mb={4}>
                    Deductions
                  </Text>
                  <Table variant="simple">
                    <Thead>
                      <Tr>
                        <Th>Deduction Type</Th>
                        <Th isNumeric>Amount</Th>
                      </Tr>
                    </Thead>
                    <Tbody>
                      {payslipDetail.deductions.map((deduction) => (
                        <Tr key={deduction.deductionID}>
                          <Td>{deduction.deductionType}</Td>
                          <Td isNumeric>{formatCurrency(deduction.amount)}</Td>
                        </Tr>
                      ))}
                      <Tr>
                        <Td>Total</Td>
                        <Td isNumeric fontWeight="bold" color="red">
                          {formatCurrency(
                            payslipDetail.deductions.reduce(
                              (sum, deduction) => sum + deduction.amount,
                              0
                            )
                          )}
                        </Td>
                      </Tr>
                    </Tbody>
                  </Table>
                </Box>
              </HStack>
            </VStack>
          ) : (
            <Box textAlign="center" p={4}>
              Cannot find payslip detail
            </Box>
          )}
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default PayslipDetail;
