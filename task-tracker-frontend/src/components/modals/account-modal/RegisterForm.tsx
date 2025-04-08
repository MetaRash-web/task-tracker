import { useState } from "react";
import { useAuth } from "@/features/auth/hooks/use-auth";

interface RegisterFormProps {
  onClose: () => void;
  onSwitchToLogin: () => void;
}

export const RegisterForm = ({ onClose, onSwitchToLogin }: RegisterFormProps) => {
  const [formData, setFormData] = useState({
    email: '',
    username: '',
    password: '',
    confirmPassword: ''
  });
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const { register } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError('');
    
    if (formData.password !== formData.confirmPassword) {
      setError('Пароли не совпадают');
      setIsLoading(false);
      return;
    }
    
    const result = await register(
      formData.email,
      formData.username,
      formData.password
    );
    
    if (result.success) {
      onClose();
    } else {
      setError(result.error ?? null);
    }
    
    setIsLoading(false);
  };


  return (
    <div className="space-y-4">
      <h2 className="text-xl font-bold mb-4">Регистрация</h2>
      
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
            type="text"
            placeholder="Имя пользователя"
            className="w-full p-2 border rounded"
            value={formData.username}
            onChange={(e) => setFormData({...formData, username: e.target.value})}
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

        <div>
          <input
            type="password"
            placeholder="Подтвердите пароль"
            className="w-full p-2 border rounded"
            value={formData.confirmPassword}
            onChange={(e) => setFormData({...formData, confirmPassword: e.target.value})}
            required
            disabled={isLoading}
          />
        </div>

        <button
          type="submit"
          disabled={isLoading}
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 disabled:opacity-50"
        >
          {isLoading ? 'Регистрация...' : 'Зарегистрироваться'}
        </button>
      </form>

      <button 
        onClick={onSwitchToLogin}
        className="text-blue-600 hover:underline"
        disabled={isLoading}
      >
        Уже есть аккаунт? Войти
      </button>
    </div>
  );
};