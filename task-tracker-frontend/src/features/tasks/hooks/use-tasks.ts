import { useTasksContext } from '@/features/tasks/TasksProvider';
import { useTaskActions } from '@/features/tasks/hooks/use-task-actions';

export const useTasks = () => {
  const context = useTasksContext();
  const actions = useTaskActions();

  return {
    ...context,
    ...actions
  };
};