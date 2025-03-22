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
  Select,
  Button,
  VStack,
  useToast,
} from "@chakra-ui/react";
import {
  createTimesheet,
  updateTimesheet,
} from "../../controller/attendanceController";

const TimekeepingFormModal = ({ isOpen, onClose, timesheet, onSubmit }) => {
  const [formData, setFormData] = useState({
    name: "",
    timePeriod: "",
    status: "To do",
    lockStatus: "Unlock",
    note: "",
  });
  const toast = useToast();

  useEffect(() => {
    if (timesheet) {
      setFormData({
        timesheetID: timesheet.timesheetID || "",
        name: timesheet.name || "",
        timePeriod: timesheet.timePeriod
          ? new Date(timesheet.timePeriod).toISOString().split("T")[0]
          : "",
        status: timesheet.status || "To do",
        lockStatus: timesheet.lockStatus || "Unlock",
        note: timesheet.note || "",
        employeeID: timesheet.employeeID || "",
      });
    } else {
      setFormData({
        timesheetID: "",
        name: "",
        timePeriod: "",
        status: "To do",
        lockStatus: "Unlock",
        note: "",
        employeeID: "",
      });
    }
  }, [timesheet]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (timesheet) {
        await updateTimesheet(formData.timesheetID, formData);
      } else {
        await createTimesheet(formData);
      }
      onSubmit();
      onClose();
      toast({
        title: "Success",
        description: timesheet
          ? "Update timesheet successfully"
          : "Create new timesheet successfully",
        status: "success",
        duration: 3000,
        isClosable: true,
      });
    } catch (error) {
      console.error("Error submitting form:", error);
      toast({
        title: "Error",
        description: timesheet
          ? "Cannot update timesheet"
          : "Cannot create new timesheet",
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
          {timesheet ? "Edit timesheet" : "Create new timesheet"}
        </ModalHeader>
        <ModalCloseButton />
        <ModalBody pb={6}>
          <form onSubmit={handleSubmit}>
            <VStack spacing={4}>
              <FormControl isRequired>
                <FormLabel>Timesheet Name</FormLabel>
                <Input
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  placeholder="Enter timesheet name"
                />
              </FormControl>

              <FormControl isRequired>
                <FormLabel>Time Period</FormLabel>
                <Input
                  type="date"
                  name="timePeriod"
                  value={formData.timePeriod}
                  onChange={handleChange}
                />
              </FormControl>

              <FormControl isRequired>
                <FormLabel>Status</FormLabel>
                <Select
                  name="status"
                  value={formData.status}
                  onChange={handleChange}
                >
                  <option value="To do">Waiting for processing</option>
                  <option value="Process">Processing</option>
                  <option value="Done">Completed</option>
                </Select>
              </FormControl>

              <FormControl isRequired>
                <FormLabel>Lock Status</FormLabel>
                <Select
                  name="lockStatus"
                  value={formData.lockStatus}
                  onChange={handleChange}
                >
                  <option value="Unlock">Unlock</option>
                  <option value="Lock">Lock</option>
                </Select>
              </FormControl>

              <FormControl>
                <FormLabel>Note</FormLabel>
                <Input
                  name="note"
                  value={formData.note}
                  onChange={handleChange}
                  placeholder="Enter note"
                />
              </FormControl>

              <FormControl>
                <FormLabel>Employee</FormLabel>
                <Input
                  name="employeeID"
                  value={formData.employeeID}
                  onChange={handleChange}
                  placeholder="Enter employee ID"
                />
              </FormControl>
              <Button type="submit" colorScheme="blue" w="full">
                {timesheet ? "Update" : "Create"}
              </Button>
            </VStack>
          </form>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default TimekeepingFormModal;
