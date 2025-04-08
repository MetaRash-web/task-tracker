import { apiClient } from '@/api/client/client';
import { PaginatedResponse, Task } from '@/api/tasks/tasks.types';

export const TaskService = {
  async getTasks(page: number = 0, size: number = 25): Promise<PaginatedResponse<Task>> {
    const params = new URLSearchParams();
    params.append('page', page.toString());
    params.append('size', size.toString());
    
    const data = await apiClient.get<PaginatedResponse<Task>>(
      `/tasks?${params.toString()}`
    );
    return data;
},

  async createTask(taskData: Omit<Task, "id" | "createdAt">): Promise<Task> {
    const data = await apiClient.post<Task>('/tasks', taskData);
    return data;
  },

  async updateTask(taskData: Partial<Task>): Promise<Task> {
    const data = await apiClient.put<Task>(`/tasks/${taskData.id}`, taskData);
    return data;
  },

  async deleteTask(taskId: number): Promise<void> {
    await apiClient.delete(`/tasks/${taskId}`);
  }
};