import { User } from "@/api/auth/auth.types";
import { apiClient } from "@/api/client/client";

export const AuthService = {
    async login(email: string, password: string) {
      const data = await apiClient.post<{ 
        user: User; 
        token: string;
        refreshToken: string;
      }>("/auth/login", { email, password });
      return data;
    },
  
    async register(email: string, username: string, password: string) {
      const data = await apiClient.post<{ 
        user: User;
        token: string;
        refreshToken: string;
      }>("/user", { email, username, password });
      return data;
    },
  
    async refreshToken(refreshToken: string) {
      const data = await apiClient.post<{
        token: string;
        refreshToken?: string;
      }>("/auth/refresh", { refreshToken });
      return data;
    },

    async updateUser(userId: number, updateData: {
      username?: string;
      email?: string;
      telegram?: string;
      password?: string;
    }) {
      console.log("update data: ", updateData);
      const data = await apiClient.patch<{ user: User }>(
        `/users/${userId}`, updateData);
      return data;
    },
  
    async deleteUser(userId: number) {
      await apiClient.delete(`/users/${userId}`);
    },
  
    async logout() {
      await apiClient.post("/auth/logout");
    }
  };