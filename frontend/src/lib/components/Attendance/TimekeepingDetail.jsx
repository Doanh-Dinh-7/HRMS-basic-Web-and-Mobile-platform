import React, { useState, useEffect } from "react";
import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalCloseButton,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  Badge,
  useToast,
  Spinner,
  Box,
  VStack,
  Text,
  HStack,
  Divider,
} from "@chakra-ui/react";
import { getTimesheetDetail } from "../../controller/attendanceController";

const TimekeepingDetail = ({ isOpen, onClose, timesheetId }) => {
  const [timesheetDetail, setTimesheetDetail] = useState(null);
  const [loading, setLoading] = useState(true);
  const toast = useToast();

  useEffect(() => {
    const fetchTimesheetDetail = async () => {
      if (timesheetId) {
        try {
          setLoading(true);
          const response = await getTimesheetDetail(timesheetId);
          setTimesheetDetail(response);
        } catch (error) {
          console.error("Error fetching timesheet detail:", error);
          toast({
            title: "Lỗi",
            description: "Không thể tải thông tin chi tiết timesheet",
            status: "error",
            duration: 3000,
            isClosable: true,
          });
        } finally {
          setLoading(false);
        }
      }
    };

    fetchTimesheetDetail();
  }, [timesheetId, toast]);

  const getStatusColor = (status) => {
    switch (status) {
      case "Done":
        return "green";
      case "Process":
        return "blue";
      case "To do":
        return "yellow";
      default:
        return "gray";
    }
  };

  const getStatusText = (status) => {
    switch (status) {
      case "Done":
        return "Hoàn thành";
      case "Process":
        return "Đang xử lý";
      case "To do":
        return "Chờ xử lý";
      default:
        return status;
    }
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString("vi-VN", {
      weekday: "long",
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose} size="xl">
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Chi tiết chấm công</ModalHeader>
        <ModalCloseButton />
        <ModalBody pb={6}>
          {loading ? (
            <Box display="flex" justifyContent="center" p={4}>
              <Spinner size="xl" />
            </Box>
          ) : timesheetDetail ? (
            <VStack spacing={6} align="stretch">
              {/* Mainbar */}
              <Box p={4} bg="gray.50" borderRadius="md">
                <VStack align="stretch" spacing={2}>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Tên timesheet:</Text>
                    <Text>{timesheetDetail.name}</Text>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Thời gian:</Text>
                    <Text>{formatDate(timesheetDetail.timePeriod)}</Text>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Trạng thái:</Text>
                    <Badge colorScheme={getStatusColor(timesheetDetail.status)}>
                      {getStatusText(timesheetDetail.status)}
                    </Badge>
                  </HStack>
                  <HStack justify="space-between">
                    <Text fontWeight="bold">Khóa:</Text>
                    <Text>{timesheetDetail.lockStatus}</Text>
                  </HStack>
                  {timesheetDetail.note && (
                    <HStack justify="space-between">
                      <Text fontWeight="bold">Ghi chú:</Text>
                      <Text>{timesheetDetail.note}</Text>
                    </HStack>
                  )}
                </VStack>
              </Box>

              <Divider />

              {/* Main Table */}
              <Table variant="simple">
                <Thead>
                  <Tr>
                    <Th>STT</Th>
                    <Th>Mã nhân viên</Th>
                    <Th>Tên nhân viên</Th>
                    <Th>Số giờ làm việc</Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {timesheetDetail.dayTimekeeping.map((record, index) => (
                    <Tr key={record.recordID}>
                      <Td>{index + 1}</Td>
                      <Td>{record.employeeID}</Td>
                      <Td>{record.fullName}</Td>
                      <Td>{record.hoursOfWork}</Td>
                    </Tr>
                  ))}
                </Tbody>
              </Table>
            </VStack>
          ) : (
            <Box textAlign="center" p={4}>
              Không tìm thấy thông tin chi tiết
            </Box>
          )}
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default TimekeepingDetail;
