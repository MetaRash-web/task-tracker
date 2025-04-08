"use client";

import { useState } from "react";
import { Task } from "@/api/tasks/tasks.types";
import { formatForBackend } from "@/lib/utils/date.utils";
import { TaskPriority, TaskStatus } from "@/lib/constants/task.constants";

const TASK_STATUS_OPTIONS = [
  { value: "pending", label: "Ожидает" },
  { value: "in_progress", label: "В работе" },
  { value: "completed", label: "Завершена" }
] as const;

const TASK_PRIORITY_OPTIONS = [
  { value: "high", label: "Высокий" },
  { value: "medium", label: "Средний" },
  { value: "low", label: "Низкий" }
] as const;

interface TaskFormData {
  title: string;
  description: string;
  status: TaskStatus;
  priority: TaskPriority;
  dueDate: string;
}

interface AddTaskModalProps {
  onClose: () => void;
  onAddTask: (task: Omit<Task, "id" | "createdAt" | "username">) => void;
}

const AddTaskModal = ({ onClose, onAddTask }: AddTaskModalProps) => {
  const [form, setForm] = useState<TaskFormData>({
    title: "",
    description: "",
    status: "pending",
    priority: "medium",
    dueDate: ""
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    const dueDate = form.dueDate ? formatForBackend(form.dueDate) : undefined;

    onAddTask({
      ...form,
      dueDate,
    });
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-xl w-full max-w-md">
        <h2 className="text-xl font-bold mb-4">Новая задача</h2>
        
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block mb-1 text-sm font-medium">Название</label>
            <input
              type="text"
              value={form.title}
              onChange={(e) => setForm({...form, title: e.target.value})}
              className="w-full p-2 border rounded"
              required
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium">Описание</label>
            <textarea
              value={form.description}
              onChange={(e) => setForm({...form, description: e.target.value})}
              className="w-full p-2 border rounded min-h-[100px]"
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block mb-1 text-sm font-medium">Статус</label>
              <select
                value={form.status}
                onChange={(e) => setForm({...form, status: e.target.value as TaskStatus})}
                className="w-full p-2 border rounded"
              >
                {TASK_STATUS_OPTIONS.map((option) => (
                  <option key={option.value} value={option.value}>
                    {option.label}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block mb-1 text-sm font-medium">Приоритет</label>
              <select
                value={form.priority}
                onChange={(e) => setForm({...form, priority: e.target.value as TaskPriority})}
                className="w-full p-2 border rounded"
              >
                {TASK_PRIORITY_OPTIONS.map((option) => (
                  <option key={option.value} value={option.value}>
                    {option.label}
                  </option>
                ))}
              </select>
            </div>
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium">Срок выполнения</label>
            <input
              type="datetime-local"
              value={form.dueDate}
              onChange={(e) => setForm({...form, dueDate: e.target.value})}
              className="w-full p-2 border rounded"
            />
          </div>

          <div className="flex justify-end gap-2 pt-4">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-gray-600 hover:bg-gray-100 rounded cursor-pointer"
            >
              Отмена
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 cursor-pointer"
            >
              Добавить
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddTaskModal;