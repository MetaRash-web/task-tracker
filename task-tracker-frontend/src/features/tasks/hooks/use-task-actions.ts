import { useCallback } from 'react';
import { useTasksContext } from '@/features/tasks/TasksProvider';
import { TaskService } from '@/api/tasks/tasks';
import type { Task } from '@/api/tasks/tasks.types';

export const useTaskActions = () => {
    const { 
      pagination,
      actions: { setTasks, setPagination, setLoading, setError }
    } = useTasksContext();
    
    const fetchTasks = useCallback(async (page: number = 0) => {
      setLoading(true);
      setError(null);
      try {
        const data = await TaskService.getTasks(page);
        setTasks(data.content);
        setPagination({ page, last: data.last, first: data.first });
      } catch (err) {
        setError('Ошибка при загрузке задач');
        throw err;
      } finally {
        setLoading(false);
      }
    }, [setTasks, setPagination, setLoading, setError]);

    //Загрузка след страниц задач
    const loadNextPage = useCallback(async () => {
      await fetchTasks(pagination.currentPage + 1);
    }, [fetchTasks, pagination.currentPage]);
  
    const loadPrevPage = useCallback(async () => {
      await fetchTasks(pagination.currentPage - 1);
    }, [fetchTasks, pagination.currentPage]);

  // Обновление задач
  const updateTask = useCallback(async (updatedTask: Task) => {
    try {
      setLoading(true);
      const response = await TaskService.updateTask(updatedTask);
      
      setTasks(prev => prev.map(t => 
        t.id === updatedTask.id ? { 
          ...response, 
          createdAt: new Date(response.createdAt),
          dueDate: response.dueDate ? new Date(response.dueDate) : undefined
        } : t
      ));
      
      return response;
    } catch (err) {
      setError('Ошибка при обновлении задачи');
      console.error(err);
      throw err;
    } finally {
      setLoading(false);
    }
  }, [setTasks, setLoading, setError]);

  // Добавление новой задачи
  const addTask = useCallback(async (newTask: Omit<Task, "id" | "createdAt">) => {
    try {
      setLoading(true);
      const createdTask = await TaskService.createTask(newTask);
      setTasks(prev => [{
        ...createdTask,
        createdAt: new Date(createdTask.createdAt),
        dueDate: createdTask.dueDate ? new Date(createdTask.dueDate) : undefined
      }, ...prev]);
      return createdTask;
    } catch (error) {
      setError('Ошибка при создании задачи');
      throw error;
    } finally {
      setLoading(false);
    }
  }, [setTasks, setLoading, setError]);

  // Удаление задачи
  const deleteTask = useCallback(async (taskId: number) => {
    try {
      setLoading(true);
      await TaskService.deleteTask(taskId);
      setTasks(prev => prev.filter(task => task.id !== taskId));
    } catch (err) {
      setError('Ошибка при удалении задачи');
      console.error(err);
      throw err;
    } finally {
      setLoading(false);
    }
  }, [setTasks, setLoading, setError]);
  
  return {
    fetchTasks,
    loadNextPage,
    loadPrevPage,
    updateTask,
    addTask,
    deleteTask,
  };
}