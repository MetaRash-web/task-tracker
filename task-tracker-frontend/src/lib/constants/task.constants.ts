export const TASK_STATUSES = {
    PENDING: "pending",
    IN_PROGRESS: "in_progress",
    COMPLETED: "completed"
  } as const;
  
  export const TASK_PRIORITIES = {
    HIGH: "high",
    MEDIUM: "medium",
    LOW: "low"
  } as const;
  
  export type TaskStatus = typeof TASK_STATUSES[keyof typeof TASK_STATUSES];
  export type TaskPriority = typeof TASK_PRIORITIES[keyof typeof TASK_PRIORITIES];
