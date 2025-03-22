import { createBrowserRouter } from "react-router-dom";
import ErrorPage from "../../pages/Error";
import Employees from "../../pages/Employees";
import Layout from "../../lib/components/Layout/Layout";
import Timekeeping from "../../pages/Timekeeping";
import Payslips from "../../pages/Payslips";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/employees",
        element: <Employees />,
      },
      {
        path: "/timekeeping",
        element: <Timekeeping />,
      },
      {
        path: "/payslips",
        element: <Payslips />,
      },
    ],
  },
]);
