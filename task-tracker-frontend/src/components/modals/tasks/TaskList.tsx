import React, { useState } from "react";
import { Task } from "@/api/tasks/tasks.types";
import TaskCard from "@/components/modals/tasks/TaskCard";
import EditTaskModal from "@/components/modals/tasks/EditTaskModal";

interface TaskListProps {
  tasks: Task[];
  onTaskUpdate: (updatedTask: Task) => void;
  onTaskDelete: (taskId: number) => void;
  pagination: {
    current: number;
    hasNext: boolean;
    hasPrev: boolean;
    onNext: () => void;
    onPrev: () => void;
  };
}

const TaskList: React.FC<TaskListProps> = ({ 
  tasks, 
  onTaskUpdate,
  onTaskDelete,
  pagination
}) => {
  const [editingTask, setEditingTask] = useState<Task | null>(null);

  const hasPages = pagination.hasNext || pagination.hasPrev;

  return (
    <div className="space-y-4">
      <div className="flex gap-4 flex-wrap">
      {tasks.map((task) => (
        <TaskCard 
          key={task.id}
          task={task} 
          onClick={() => setEditingTask(task)}
          onDelete={onTaskDelete}
        />
      ))}
      </div>

      {hasPages && <div className="flex items-center justify-center gap-4 mt-6">
        {pagination.hasPrev && (
          <button
            onClick={pagination.onPrev}
            className="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-colors"
          >
            Назад
          </button>
        )}
        
        <span className="px-3 py-1 text-sm font-medium text-gray-700 bg-gray-100 rounded-md">
          Страница {pagination.current + 1}
        </span>
        
        {pagination.hasNext && (
          <button
            onClick={pagination.onNext}
            className="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-colors"
          >
            Вперед
          </button>
        )}
      </div>}

      {editingTask && (
        <EditTaskModal
          task={editingTask}
          onClose={() => setEditingTask(null)}
          onSave={(updatedTask) => {
            onTaskUpdate(updatedTask);
            setEditingTask(null);
          }}
        />
      )}
    </div>
  );
};

export default TaskList;