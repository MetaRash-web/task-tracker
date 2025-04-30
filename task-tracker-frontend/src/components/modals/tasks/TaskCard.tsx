"use client";

import React from "react";
import { Task } from "@/api/tasks/tasks.types";
import { formatTaskDate } from "@/lib/utils/date.utils";
import { TaskPriority, TaskStatus } from "@/lib/constants/task.constants";

interface TaskCardProps {
  task: Task;
  onClick: () => void;
  onDelete: (taskId: number) => void;
}

const statusColors: Record<TaskStatus, string> = {
  pending: "bg-yellow-100 text-yellow-800",
  in_progress: "bg-blue-100 text-blue-800",
  completed: "bg-green-100 text-green-800",
};

const priorityIcons: Record<TaskPriority, string> = {
  high: "🔴",
  medium: "🟡",
  low: "⚪",
};

const TaskCard: React.FC<TaskCardProps> = ({ task, onClick, onDelete }) => {
  const safeStatus = task.status || 'pending';
  const safePriority = task.priority || 'medium';

  const handleDelete = (e: React.MouseEvent) => {
    e.stopPropagation(); // Блокируем всплытие, чтобы не срабатывал onClick карточки
    onDelete(task.id);
  };
  
  return (
    <div 
      onClick={onClick}
      className="w-auto h-36 p-4 rounded-2xl shadow-xl bg-white
        transition-all duration-300 ease-in-out
        hover:-translate-y-1 hover:shadow-2xl
        active:translate-y-0 active:shadow-lg
        cursor-pointer"
    >
      <div className="flex justify-between items-start mb-2">
        <div className="flex items-center gap-2">
          <span>{priorityIcons[safePriority]}</span>
          <h3 className="font-semibold text-gray-800 truncate">{task.title}</h3>
        </div>
        <span className={`text-xs px-2 ml-5 py-1 rounded-full ${statusColors[safeStatus]}`}>
          {safeStatus.toLowerCase()}
        </span>
      </div>

      <button
        onClick={handleDelete}
        className="absolute bottom-2 right-2 p-1 text-gray-400 hover:text-red-500
          focus:outline-none focus:ring-1 focus:ring-red-300 rounded-full"
        aria-label="Удалить задачу"
      >
        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
        </svg>
      </button>

      {task.description && (
        <p className="text-sm text-gray-600 mt-1 line-clamp-2 mb-4">
          {task.description}
        </p>
      )}

      <div className="space-y-1 text-xs text-gray-500">
        {task.dueDate && (
          <div className="flex items-center gap-1">
            <span>📅</span>
            <span>До: {formatTaskDate(task.dueDate)}</span>
          </div>
        )}
        
        <div className="flex items-center gap-1">
          <span>🕒</span>
          <span>Создана: {formatTaskDate(task.createdAt as string | Date | null | undefined)}</span>
        </div>
      </div>
    </div>
  );
};

export default TaskCard;