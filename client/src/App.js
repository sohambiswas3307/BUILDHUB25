import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LandingPage from './components/LandingPage';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import CustomerDashboard from './components/dashboards/CustomerDashboard';
import ContractorDashboard from './components/dashboards/ContractorDashboard';
import LabourDashboard from './components/dashboards/LabourDashboard';
import AuthContext from './context/AuthContext';

function App() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const userData = localStorage.getItem('user');
    
    if (token && userData) {
      setUser(JSON.parse(userData));
    }
    setLoading(false);
  }, []);

  const ProtectedRoute = ({ children }) => {
    if (!user) {
      return <Navigate to="/login" />;
    }
    return children;
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      <Router>
        <Routes>
          <Route path="/" element={!user ? <LandingPage /> : 
            user?.role === 'customer' ? <Navigate to="/dashboard" /> :
            user?.role === 'contractor' ? <Navigate to="/contractor-dashboard" /> :
            <Navigate to="/labour-dashboard" />
          } />
          <Route path="/login" element={!user ? <Login /> : <Navigate to="/" />} />
          <Route path="/register" element={!user ? <Register /> : <Navigate to="/" />} />
          
          <Route 
            path="/dashboard" 
            element={
              <ProtectedRoute>
                {user?.role === 'customer' ? <CustomerDashboard /> : <Navigate to="/" />}
              </ProtectedRoute>
            } 
          />
          
          <Route 
            path="/contractor-dashboard" 
            element={
              <ProtectedRoute>
                {user?.role === 'contractor' ? <ContractorDashboard /> : <Navigate to="/" />}
              </ProtectedRoute>
            } 
          />
          
          <Route 
            path="/labour-dashboard" 
            element={
              <ProtectedRoute>
                {user?.role === 'labour' ? <LabourDashboard /> : <Navigate to="/" />}
              </ProtectedRoute>
            } 
          />
        </Routes>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;

