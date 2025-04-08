import { TaskPriority, TaskStatus } from "@/lib/constants/task.constants";

    export interface Task {
    id: number;
    title: string;
    description?: string;
    status: TaskStatus;
    priority: TaskPriority;
    createdAt: string | number | Date;
    dueDate?: string | Date | null;
    completedAt?: string | Date | null;
    username?: string;
  }
  export interface PaginatedResponse<T> {
    first: boolean;
    totalElements: any;
    size: any;
    content: T[];
    pageable: {
      pageNumber: number;
      pageSize: number;
    };
    totalPages: number;
    last: boolean;
  }