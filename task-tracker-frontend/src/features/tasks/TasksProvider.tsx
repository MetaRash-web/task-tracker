"use client";

import React, { useCallback, useMemo, useState } from 'react';
import type { Task } from '@/api/tasks/tasks.types';

type PaginationState = {
    currentPage: number;
    hasNext: boolean;
    hasPrev: boolean;
  };

type TasksContextType = {
    tasks: Task[];
    pagination: {
      currentPage: number;
      hasNext: boolean;
      hasPrev: boolean;
    };
    loading: boolean;
    error: string | null;
    actions: {
      setTasks: React.Dispatch<React.SetStateAction<Task[]>>;
      setPagination: (data: { page: number; last: boolean; first: boolean }) => void;
      setLoading: (value: boolean) => void;
      setError: (message: string | null) => void;
    };
  };
  
  const TasksContext = React.createContext<TasksContextType | undefined>(undefined);
  
  export const TasksProvider = ({ children }: { children: React.ReactNode }) => {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [pagination, setPaginationState] = useState<PaginationState>({
        currentPage: 0,
        hasNext: false,
        hasPrev: false,
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
  
    const updatePagination = useCallback(
        (data: { page: number; last: boolean; first: boolean }) => {
          setPaginationState({
            currentPage: data.page,
            hasNext: !data.last,
            hasPrev: !data.first,
          });
        },
        []
      );
    
      const value = useMemo(() => ({
        tasks,
        pagination,
        loading,
        error,
        actions: {
          setTasks,
          setPagination: updatePagination,
          setLoading,
          setError,
        },
      }), [tasks, pagination, loading, error, updatePagination]);
  
    return <TasksContext.Provider value={value}>{children}</TasksContext.Provider>;
  };

export const useTasksContext = () => {
  const context = React.useContext(TasksContext);
  if (!context) {
    throw new Error('useTasksContext must be used within a TasksProvider');
  }
  return context;
};