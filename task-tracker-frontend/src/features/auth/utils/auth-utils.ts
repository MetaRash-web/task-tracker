import { User } from "@/api/auth/auth.types";
import axios from "axios";

export const handleAuthError = (error: unknown) => {
    return axios.isAxiosError(error) 
      ? error.response?.data?.message || 'Ошибка авторизации'
      : 'Неизвестная ошибка';
  };

export const saveAuthDataToStorage = (
  user: User,
  accessToken: string,
  refreshToken: string
) => {
  localStorage.setItem('user', JSON.stringify(user));
  localStorage.setItem('accessToken', accessToken);
  localStorage.setItem('refreshToken', refreshToken);
};

export const removeAuthDataFromStorage = () => {
    localStorage.removeItem('user');
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
}