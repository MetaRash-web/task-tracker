"use client";

import { useAuth } from "@/features/auth/hooks/use-auth";
import LoginForm from "./LoginForm";
import { RegisterForm } from "./RegisterForm";
import { useState } from "react";
import { AccountForm } from "./AccountForm";
import toast from "react-hot-toast";

interface AccountModalProps {
  onClose: () => void;
}

export const AccountModal = ({ onClose }: AccountModalProps) => {
  const { isAuth, user, updateUser, deleteUser, logout } = useAuth();
  const [isLoginForm, setIsLoginForm] = useState(true);
  
  const handleUpdate = async (data: { username?: string; email?: string; telegram?: string }) => {
    try {
      updateUser(user.id, data);
      toast.success("Данные обновлены!");
      onClose();
    } catch (error) {
      toast.error("Ошибка при обновлении");
    }
  };

  const handleDelete = async () => {
    if (confirm("Удалить аккаунт безвозвратно?")) {
      deleteUser(user.id);
      onClose();
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div 
        className="absolute inset-0 bg-black/50" 
        onClick={onClose}
      />
      
      <div className="relative bg-white rounded-2xl w-full max-w-md p-6 shadow-2xl">
        {isAuth && user ? (
          <AccountForm 
            initialData={{ 
              username: user.username, 
              email: user.email, 
              telegram: user.telegram 
            }} 
            onUpdate={handleUpdate} 
            onDelete={handleDelete} 
            onLogout={() => {logout(); onClose();}} 
          />
        ) : isLoginForm ? (
          <LoginForm 
            onClose={onClose} 
            onSwitchToRegister={() => setIsLoginForm(false)} 
          />
        ) : (
          <RegisterForm 
            onClose={onClose} 
            onSwitchToLogin={() => setIsLoginForm(true)} 
          />
        )}
      </div>
    </div>
  );
};

export default AccountModal;