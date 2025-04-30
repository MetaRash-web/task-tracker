import { Task } from "@/api/tasks/tasks.types";

export type ModalType = 
  | "AddTaskModal"
  | "EditTaskModal"
  | "SettingsModal"
  | "AccountModal"
  | "NotificationsModal";

export type ModalPropsMap = {
  AddTaskModal: {
    onAddTask: (task: Omit<Task, "id" | "createdAt">) => void;
  };
  EditTaskModal: {
    task: Task;
    onSave: (task: Task) => void;
  };
  SettingsModal: {
    initialSettings?: {
      darkMode?: boolean;
      notifications?: boolean;
    };
  };
  AccountModal: {
  };
  NotificationsModal: {
  };
};