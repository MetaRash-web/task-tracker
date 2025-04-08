import { useAuthContext } from '@/features/auth/AuthProvider';
import { useAuthActions } from '@/features/auth/hooks/use-auth-actions';

export const useAuth = () => {
  const state = useAuthContext();
  const actions = useAuthActions();

  return {
    ...state,
    ...actions,
  };
};