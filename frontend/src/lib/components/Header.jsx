import { Button, Container, Flex, Input, Select, Text } from "@chakra-ui/react";
import { getTenSach } from "../controller/sachController";
import { useState } from "react";
// import { IoMoon } from "react-icons/io5";
// import { LuSun } from "react-icons/lu";

const Header = (refreshData, setDanhSach) => {
  const [tenSach, setTenSach] = useState("");

  const handleTenSach = () => {
    getTenSach(tenSach).then((data) => {
      console.log(data);

      setDanhSach([]);
      setDanhSach(data);
    });
  };

  return (
    <Container maxW={"1200px"}>
      <form action="" method="get">
        <Flex
          justifyContent={"space-around"}
          align={"center"}
          p="4"
          border={"1px solid gray"}
          gap={"5"}
        >
          <Flex gap={"4"} direction={"column"} alignItems={"flex-start"}>
            <Text fontSize={"sm"}>Tên sách</Text>
            <Input
              type="text"
              placeholder="Nhập tên sách"
              onChange={(e) => setTenSach(e.target.value)}
            />
          </Flex>
          <Flex gap={"4"} direction={"column"} alignItems={"flex-start"}>
            <Text fontSize={"sm"}>Tên tác giả</Text>
            <Select>
              <option value="#" selected>
                Chọn tác giả
              </option>
            </Select>
          </Flex>
          <Flex gap={"4"} direction={"column"} alignItems={"flex-start"}>
            <Text fontSize={"sm"}>Thể loại</Text>
            <Select placeholder={"Chọn thể loại"}>
              <option value="#" selected>
                Chọn thể loại
              </option>
            </Select>
          </Flex>
          <Flex gap={"5"}>
            <Button
              variant={"solid"}
              colorScheme={"blue"}
              onClick={handleTenSach}
            >
              Search
            </Button>
            <Button
              variant={"solid"}
              colorScheme={"blue"}
              onClick={refreshData}
            >
              Reload
            </Button>
          </Flex>
        </Flex>
      </form>
    </Container>
  );
};

export default Header;
