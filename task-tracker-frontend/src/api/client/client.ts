import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

const BASE_URL = `/api`;

const defaultConfig: AxiosRequestConfig = {
  baseURL: BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: false,
};

class ApiClient {
  private instance: AxiosInstance;

  constructor() {
    this.instance = axios.create(defaultConfig);
    this.setupInterceptors();
  }

  private setupInterceptors() {
    this.instance.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('accessToken');
        console.log("access token: ", token);
        if (token && token !== 'undefined') {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );
    this.instance.interceptors.response.use(
      (response) => response,
      async (error) => {
        const originalRequest = error.config;

        console.log("error status: ", error.response?.status);
        console.log("error response: ", error.response);
        
        if (error.response?.status === 401 && !originalRequest._retry) {
          originalRequest._retry = true;
          console.log("getting refresh token");
          const refreshToken = localStorage.getItem('refreshToken');
          
          if (!refreshToken) {
            console.log("refresh token is null, deleting access token")
            localStorage.removeItem('accessToken');
            return Promise.reject(error);
          }
  
          try {
            console.log("trying send refresh token api")
            const { data } = await axios.post(`${BASE_URL}/auth/refresh`, { refreshToken });
            
            localStorage.setItem('accessToken', data.token);
            if (data.refreshToken) {
              localStorage.setItem('refreshToken', data.refreshToken);
            }
            
            originalRequest.headers.Authorization = `Bearer ${data.token}`;
            return this.instance(originalRequest);
          } catch (refreshError) {
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            return Promise.reject(refreshError);
          }
        }
        
        return Promise.reject(error);
      }
    );
  }

  public async get<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    const response = await this.instance.get<T>(url, config);
    return response.data;
  }

  public async post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    const response = await this.instance.post<T>(url, data, config);
    return response.data;
  }

  public async put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    const response = await this.instance.put<T>(url, data, config);
    return response.data;
  }

  public async delete<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    const response = await this.instance.delete<T>(url, config);
    return response.data;
  }

  public async patch<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    console.log("data: ", data);
    const response = await this.instance.patch<T>(url, data, config);
    return response.data;
  }
}

export const apiClient = new ApiClient();