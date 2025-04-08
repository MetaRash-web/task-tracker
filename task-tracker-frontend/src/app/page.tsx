"use client";

import { useEffect, useState } from "react";
import { ModalManager } from "@/components/modals/ModalManager";
import { ModalType } from "@/types/modal.types";
import Sidebar from "@/components/sidebar/Sidebar";
import TaskList from "@/components/modals/tasks/TaskList";
import { useTasks } from "@/features/tasks/hooks/use-tasks";
import { Task } from "@/api/tasks/tasks.types";
import { useAuth } from "@/features/auth/hooks/use-auth";

export default function Home() {
  const { isAuth } = useAuth()
  const {
    tasks,
    loading,
    pagination,
    fetchTasks,
    loadNextPage,
    loadPrevPage,
    updateTask,
    deleteTask,
    addTask
  } = useTasks();
  
  const [activeModal, setActiveModal] = useState<{
    type: ModalType | null;
    props?: any;
  }>({ type: null });

  const handleAddTask = async (newTask: Omit<Task, "id" | "createdAt">) => {
    try {
      await addTask(newTask);
      setActiveModal({ type: null });
    } catch (error) {
      console.error("Ошибка при добавлении задачи:", error);
    }
  };

  const getModalProps = () => {
    switch (activeModal.type) {
      case "AddTaskModal":
        return { onAddTask: handleAddTask };
      case "EditTaskModal":
        return activeModal.props;
      case "AccountModal":
        return { 
          initialUserData: { 
            name: "Иван Иванов", 
            email: "ivan@example.com" 
          } 
        };
      case "SettingsModal":
        return { 
          initialSettings: { 
            darkMode: false, 
          } 
        };
      default:
        return {};
    }
  };

  useEffect(() => {
    if (isAuth) {
      fetchTasks();
    }
  }, [isAuth, fetchTasks]);

  return (  
    <div className="flex h-screen w-screen">
      <Sidebar onOpenModal={(name) => setActiveModal({ type: name as ModalType })} />
      
      <div className="flex-grow p-6 bg-amber-100 w-full h-full rounded-[100px]">
        <main className="p-6">
          <h1 className="text-xl font-bold mb-4">Задачи</h1>
          
          {loading ? (
            <div>Загрузка задач...</div>
          ) : (
            <TaskList 
              tasks={tasks} 
              onTaskUpdate={updateTask}
              onTaskDelete={deleteTask}
              pagination={{
                current: pagination.currentPage,
                hasNext: pagination.hasNext,
                hasPrev: pagination.hasPrev,
                onNext: loadNextPage,
                onPrev: loadPrevPage
              }}
            />
          )}
          
          {!loading && tasks.length === 0 && (
            <div className="text-center py-8 text-gray-500">
              Нет задач для отображения
            </div>
          )}
        </main>
      </div>

      <ModalManager
        modalType={activeModal.type}
        onClose={() => setActiveModal({ type: null })}
        modalProps={getModalProps()}
      />
    </div>
  );
}