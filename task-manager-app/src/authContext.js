import React, { createContext, useState } from "react";

export const AuthContext = createContext({ 
  userId:null, 
  role:null,
  isAuthenticated:false, 
  setLoginContext: ()=>{} ,
  setLogoutContext: ()=>{} 
});

export const AuthContextProvider = ({ children }) => {
  const [userId, setUserId] = useState(localStorage.getItem("userId"));
  // const [user, setUser] = useState(null);
  const [role, setRole] = useState(localStorage.getItem("role"));
  const [isAuthenticated, setIsAuthenticated] = useState(localStorage.getItem("isAuthenticated"));

  const setLoginContext = (user) => {
    // Authenticate user and set user information and authentication status
    localStorage.setItem("userId",user.userId)
    setRole(user.userId)
    localStorage.setItem("role",user.role)
    setRole(user.role)
    localStorage.setItem("isAuthenticated",true)
    setIsAuthenticated(true);
  };

  const logout = () => {
    // Remove user information and set authentication status to false
    localStorage.removeItem("userId")
    setRole(null)
    localStorage.removeItem("role")
    setRole(null)
    localStorage.removeItem("isAuthenticated")
    setIsAuthenticated(false);
  };

  const contextValue = {
    userId:userId, 
    role:role,
    isAuthenticated:isAuthenticated, 
    setLoginContext: setLoginContext,
    setLogoutContext:logout
  }

  return (
    <AuthContext.Provider value={contextValue}>
      {children}
    </AuthContext.Provider> 
  );
};
