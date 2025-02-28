import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div>
      <main>
        <Outlet /> {/* Các route con sẽ render tại đây */}
      </main>
    </div>
  );
};

export default Layout;
