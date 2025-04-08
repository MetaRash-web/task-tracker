import { AuthService } from '@/api/auth/auth.api';
import { useAuthContext } from '../AuthProvider';
import { UserUpdateData } from '@/api/auth/auth.types';
import { handleAuthError, removeAuthDataFromStorage, saveAuthDataToStorage } from '../utils/auth-utils';

export const useAuthActions = () => {
  const { actions } = useAuthContext();

  const login = async (email: string, password: string) => {
    try {
      const data = await AuthService.login(email, password);
      saveAuthDataToStorage(data.user, data.token, data.refreshToken);
      actions.setAuthData(data.user, true);
      return { success: true };
    } catch (error) {
      return { success: false, error: handleAuthError(error) };
    }
  };

  const register = async (email: string, username: string, password: string) => {
    try {
      const data = await AuthService.register(email, username, password);
      saveAuthDataToStorage(data.user, data.token, data.refreshToken);
      actions.setAuthData(data.user, true);
      return { success: true };
    } catch (error) {
      return { success: false, error: handleAuthError(error) };
    }
  };

  const updateUser = async (userId: number, data: UserUpdateData) => {
    try {
      const response = await AuthService.updateUser(userId, data);
      actions.setAuthData(response.user, true);
      localStorage.setItem('user', JSON.stringify(response.user));
      return { success: true };
    } catch (error) {
      return { success: false, error: handleAuthError(error) };
    }
  };

  const deleteUser = async (userId: number) => {
    try {
      await AuthService.deleteUser(userId);
      logout();
      return { success: true };
    } catch (error) {
      return { success: false, error: handleAuthError(error) };
    }
  };

  const logout = () => {
    removeAuthDataFromStorage();
    actions.clearAuth();
    AuthService.logout().catch(console.error);
  };

  return {
    login,
    register,
    updateUser,
    deleteUser,
    logout,
  };
}

