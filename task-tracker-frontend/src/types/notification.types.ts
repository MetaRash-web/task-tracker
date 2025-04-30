export interface NotificationMessage {
    taskId: number;
    title: string;
    message: string;
    userEmail: string;
    dueDate: string; // или Date, если ты сразу парсишь строку в Date объект
  }
  