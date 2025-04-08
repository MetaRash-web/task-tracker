import { useState } from "react";
import { useAuth } from "@/features/auth/hooks/use-auth";

interface LoginFormProps {
  onClose: () => void;
  onSwitchToRegister: () => void;
}

export const LoginForm = ({ onClose, onSwitchToRegister }: LoginFormProps) => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const { login } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError('');
    
    const result = await login(formData.email, formData.password);
    
    if (result.success) {
      onClose();
    } else {
      setError(result.error ?? null);
    }
    
    setIsLoading(false);
  };

  return (
    <div className="space-y-4">
      <h2 className="text-xl font-bold mb-4">Вход</h2>
      
      {error && (
        <div className="p-2 mb-4 text-red-600 bg-red-100 rounded">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <input
            type="email"
            placeholder="Email"
            className="w-full p-2 border rounded"
            value={formData.email}
            onChange={(e) => setFormData({...formData, email: e.target.value})}
            required
            disabled={isLoading}
          />
        </div>

        <div>
          <input
            type="password"
            placeholder="Пароль"
            className="w-full p-2 border rounded"
            value={formData.password}
            onChange={(e) => setFormData({...formData, password: e.target.value})}
            required
            minLength={6}
            disabled={isLoading}
          />
        </div>

        <button
          type="submit"
          disabled={isLoading}
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 disabled:opacity-50"
        >
          {isLoading ? 'Вход...' : 'Войти'}
        </button>
      </form>

      <div className="flex justify-between items-center">
        <button 
          onClick={onSwitchToRegister}
          className="text-blue-600 hover:underline"
          disabled={isLoading}
        >
          Нет аккаунта? Зарегистрироваться
        </button>
      </div>
    </div>
  );
};

export default LoginForm;