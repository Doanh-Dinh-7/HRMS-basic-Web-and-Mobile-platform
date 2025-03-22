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
import TimesheetTable from "../lib/components/Attendance/TimesheetTable";
import TimekeepingDetail from "../lib/components/Attendance/TimekeepingDetail";
import TimekeepingFormModal from "../lib/components/Attendance/TimekeepingFormModal";
import {
  getTimesheetList,
  createTimesheet,
  updateTimesheet,
} from "../lib/controller/attendanceController";

const Timekeeping = () => {
  const [timesheets, setTimesheets] = useState([]);
  const [filteredTimesheets, setFilteredTimesheets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());
  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);
  const [isFormModalOpen, setIsFormModalOpen] = useState(false);
  const [selectedTimesheetId, setSelectedTimesheetId] = useState(null);
  const [selectedTimesheet, setSelectedTimesheet] = useState(null);
  const toast = useToast();

  // Gọi API lấy danh sách timesheet
  useEffect(() => {
    const fetchTimesheets = async () => {
      try {
        const response = await getTimesheetList();
        setTimesheets(response);
        filterTimesheetsByYear(response, selectedYear);
      } catch (error) {
        console.error("Error fetching timesheets:", error);
        toast({
          title: "Lỗi",
          description: "Không thể tải danh sách timesheet",
          status: "error",
          duration: 3000,
          isClosable: true,
        });
      } finally {
        setLoading(false);
      }
    };
    fetchTimesheets();
  }, [selectedYear, toast]);

  const filterTimesheetsByYear = (timesheetsList, year) => {
    if (year === "all") {
      setFilteredTimesheets(timesheetsList);
    } else {
      const filtered = timesheetsList.filter((timesheet) => {
        const timesheetDate = new Date(timesheet.timePeriod);
        return timesheetDate.getFullYear() === year;
      });
      setFilteredTimesheets(filtered);
    }
  };

  const handleViewTimesheet = (timesheetId) => {
    setSelectedTimesheetId(timesheetId);
    setIsDetailModalOpen(true);
  };

  const handleCloseDetailModal = () => {
    setIsDetailModalOpen(false);
    setSelectedTimesheetId(null);
  };

  const handleAddTimesheet = () => {
    setSelectedTimesheet(null);
    setIsFormModalOpen(true);
  };

  const handleEditTimesheet = (timesheet) => {
    setSelectedTimesheet(timesheet);
    setIsFormModalOpen(true);
  };

  const handleCloseFormModal = () => {
    setIsFormModalOpen(false);
    setSelectedTimesheet(null);
  };

  const handleSubmitTimesheet = async () => {
    try {
      if (selectedTimesheet) {
        await updateTimesheet(selectedTimesheet.timesheetID, selectedTimesheet);
      } else {
        await createTimesheet(selectedTimesheet);
      }
      handleCloseFormModal();
      window.location.reload();
    } catch (error) {
      console.error("Error submitting timesheet:", error);
    }
  };

  const handleYearChange = (event) => {
    const value = event.target.value;
    const year = value === "all" ? value : parseInt(value);
    setSelectedYear(year);
    filterTimesheetsByYear(timesheets, year);
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
        <Heading size="lg">Timekeeping Management</Heading>
        <Button
          leftIcon={<FiPlus />}
          colorScheme="black"
          bg="black"
          onClick={handleAddTimesheet}
        >
          New Timesheet
        </Button>
      </Flex>

      <Flex mb={6} gap={4} align="center">
        {/* Select năm, có thể để null*/}
        <Select
          placeholder="Chọn năm"
          maxW="180px"
          value={selectedYear || "all"}
          onChange={handleYearChange}
        >
          <option value="all">ALL</option>
          {Array.from(
            { length: 5 },
            (_, i) => new Date().getFullYear() - i
          ).map((year) => (
            <option key={year} value={year}>
              {year}
            </option>
          ))}
        </Select>
      </Flex>

      <TimesheetTable
        timesheets={filteredTimesheets}
        onViewTimesheet={handleViewTimesheet}
        onEditTimesheet={handleEditTimesheet}
      />

      <TimekeepingDetail
        isOpen={isDetailModalOpen}
        onClose={handleCloseDetailModal}
        timesheetId={selectedTimesheetId}
      />

      <TimekeepingFormModal
        isOpen={isFormModalOpen}
        onClose={handleCloseFormModal}
        timesheet={selectedTimesheet}
        onSubmit={handleSubmitTimesheet}
      />
    </Box>
  );
};

export default Timekeeping;
