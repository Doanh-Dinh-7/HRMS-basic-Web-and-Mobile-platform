import {
  Box,
  Button,
  Flex,
  Heading,
  Table,
  Tbody,
  Td,
  Text,
  Th,
  Thead,
  Tooltip,
  Tr,
  useToast,
} from "@chakra-ui/react";
import Header from "../lib/components/Header";
import { TfiPencilAlt } from "react-icons/tfi";
import { useEffect, useState } from "react";
import { getAllSach } from "../lib/controller/sachController";

const SearchSachPage = () => {
  const [refresh, setRefresh] = useState(false);
  const [danhSach, setDanhSach] = useState([]);
  const toast = useToast();

  useEffect(() => {
    getAllSach().then((data) => {
      setDanhSach([]);
      setDanhSach(data);
    });
  }, [refresh]);

  useEffect(() => {
    console.log("sach: ", danhSach);
  }, [danhSach]);

  // Khi cần refresh lại dữ liệu
  const refreshData = () => {
    setRefresh((prev) => !prev);
  };
  return (
    <Flex
      maxWidth={"100vw"}
      maxHeight={"100vh"}
      direction={"column"}
      textAlign={"center"}
      marginTop={"2"}
      gap={"5"}
      alignItems={"center"}
    >
      <Heading>Danh sách Sách</Heading>
      <Header refreshData={refreshData} setDanhSach={setDanhSach} />{" "}
      {/* Header chỉ hiển thị trên các route con */}
      <Box
        bg="white"
        width="1170px"
        border="1px solid #ccc"
        borderRadius="md"
        overflow="hidden"
      >
        <Box maxHeight="1170px" overflowY="auto">
          <Table size="sm" fontSize="1rem" variant="striped" width="100%">
            <Thead>
              <Tr>
                <Th>Mã sách</Th>
                <Th>Tên sách</Th>
                <Th>Thể loại</Th>
                <Th>Năm xuất bản</Th>
                <Th>Tên Tác Giả</Th>
                <Th>Hành động</Th>
              </Tr>
            </Thead>
            <Tbody>
              {danhSach.map((sach) => (
                <Tr key={sach.maSach}>
                  <Td>{sach.maSach}</Td>
                  <Td>{sach.tenSach}</Td>
                  <Td>{sach.theLoai}</Td>
                  <Td>{sach.namXuatBan}</Td>
                  <Td>{sach.tenTacGia}</Td>
                  <Td>
                    <Button
                      size="sm"
                      leftIcon={<TfiPencilAlt />}
                      colorScheme="yellow"
                      //   onClick={() => onEdit(emp)}
                    ></Button>
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        </Box>
      </Box>
    </Flex>
  );
};

export default SearchSachPage;
