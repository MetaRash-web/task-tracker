"use client"

import { createContext, useContext, useState, useMemo, useEffect } from 'react';
import type { User } from '@/api/auth/auth.types';

interface AuthContextType {
  user: User | null;
  isAuth: boolean;
  actions: {
    setAuthData: (user: User | null, isAuth: boolean) => void;
    clearAuth: () => void;
  };
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isAuth, setIsAuth] = useState(false);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    const accessToken = localStorage.getItem("accessToken");

    if (storedUser && accessToken) {
      try {
        setUser(JSON.parse(storedUser));
        setIsAuth(true);
      } catch (e) {
        console.error("Ошибка при парсинге user из localStorage", e);
        setUser(null);
        setIsAuth(false);
        localStorage.removeItem("user");
        localStorage.removeItem("accessToken");
      }
    }
  }, []);

  const actions = useMemo(() => ({
    setAuthData: (user: User | null, isAuth: boolean) => {
      setUser(user);
      setIsAuth(isAuth);
    },
    clearAuth: () => {
      setUser(null);
      setIsAuth(false);
    },
  }), []);

  const value = useMemo(() => ({
    user,
    isAuth,
    actions,
  }), [user, isAuth, actions]);

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuthContext = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuthContext must be used within AuthProvider');
  }
  return context;
};