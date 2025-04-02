import { Navigate, Outlet } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "./AuthContext";

const ProtectedRoute = () => {
  const { user, loading } = useContext(AuthContext);

  if (loading) return null; // o spinner si prefieres

  return user ? <Outlet /> : <Navigate to="/login" replace />;
};

export default ProtectedRoute;