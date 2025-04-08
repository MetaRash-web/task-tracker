export interface User {
    id?: number;
    email: string;
    username: string;
    password?: string;
    telegram?: string;
    roles?: string[];
    createdAt?: string;
    updatedAt?: string;
  }
  export interface UserUpdateData {
    username?: string;
    email?: string;
    telegram?: string;
    password?: string;
    newPassword?: string;
  }
  
  export interface AuthResponse {
    accessToken: string;
    refreshToken: string;
    user: User;
  }

  export interface ApiError {
    message: string;
    statusCode: number;
    timestamp?: string;
  }