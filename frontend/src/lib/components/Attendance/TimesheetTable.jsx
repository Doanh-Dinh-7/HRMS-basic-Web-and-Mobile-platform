import React from "react";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  IconButton,
  Badge,
  HStack,
  useToast,
} from "@chakra-ui/react";
import { FiLock, FiUnlock, FiEdit2, FiTrash2, FiEye } from "react-icons/fi";
import { deleteTimesheet } from "../../controller/attendanceController";

const TimesheetTable = ({ timesheets, onViewTimesheet, onEditTimesheet }) => {
  const toast = useToast();

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
        return "Done";
      case "Process":
        return "Process";
      case "To do":
        return "To do";
      default:
        return status;
    }
  };

  const handleDelete = async (timesheetId) => {
    if (window.confirm("Are you sure you want to delete this timesheet?")) {
      try {
        await deleteTimesheet(timesheetId);
        toast({
          title: "Success",
          description: "Timesheet deleted successfully",
          status: "success",
          duration: 3000,
          isClosable: true,
        });
      } catch (error) {
        console.error("Error deleting timesheet:", error);
        toast({
          title: "Error",
          description: "Cannot delete timesheet",
          status: "error",
          duration: 3000,
          isClosable: true,
        });
      }
    }
  };

  return (
    <Table variant="simple">
      <Thead>
        <Tr>
          <Th>ID</Th>
          <Th>Timesheet Name</Th>
          <Th>Status</Th>
          <Th>Lock</Th>
          <Th>Actions</Th>
        </Tr>
      </Thead>
      <Tbody>
        {timesheets.map((timesheet) => (
          <Tr key={timesheet.timesheetID}>
            <Td>{timesheet.timesheetID}</Td>
            <Td>{timesheet.name}</Td>
            <Td>
              <Badge colorScheme={getStatusColor(timesheet.status)}>
                {getStatusText(timesheet.status)}
              </Badge>
            </Td>
            <Td>
              {timesheet.lockStatus === "Lock" ? (
                <FiLock color="red" />
              ) : (
                <FiUnlock color="green" />
              )}
            </Td>
            <Td>
              <HStack spacing={2}>
                <IconButton
                  icon={<FiEdit2 />}
                  size="sm"
                  variant="ghost"
                  colorScheme="yellow"
                  onClick={() => onEditTimesheet(timesheet)}
                  aria-label="Edit"
                />
                <IconButton
                  icon={<FiTrash2 />}
                  size="sm"
                  variant="ghost"
                  colorScheme="red"
                  onClick={() => handleDelete(timesheet.timesheetID)}
                  aria-label="Delete"
                />
                <IconButton
                  icon={<FiEye />}
                  size="sm"
                  variant="ghost"
                  colorScheme="blue"
                  onClick={() => onViewTimesheet(timesheet.timesheetID)}
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

export default TimesheetTable;
