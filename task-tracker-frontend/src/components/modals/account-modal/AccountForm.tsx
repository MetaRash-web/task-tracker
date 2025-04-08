import { useState } from "react";
import { useForm } from "react-hook-form";
import { Pencil } from "lucide-react";
import { User, UserUpdateData } from "@/api/auth/auth.types";

interface AccountFormProps {
  initialData: User | null;
  onUpdate: (data: UserUpdateData) => Promise<void>;
  onDelete: () => Promise<void>;
  onLogout: () => void;
}

export const AccountForm = ({ initialData, onUpdate, onDelete, onLogout }: AccountFormProps) => {
  const { register, handleSubmit, setValue, watch } = useForm<UserUpdateData>({
    defaultValues: initialData ?? {}
  });

  const [isEditing, setIsEditing] = useState(false);
  const data = watch();

  const handleCancel = () => {
    setValue("username", initialData?.username ?? "");
    setValue("email", initialData?.email ?? "");
    setValue("telegram", initialData?.telegram ?? "");
    setIsEditing(false);
  };

  return (
    <div className="space-y-6">
      <div className="space-y-2">
        <div className="flex justify-between items-center">
          <h2 className="text-xl font-bold">Профиль</h2>
          {!isEditing && (
            <button onClick={() => setIsEditing(true)} className="text-gray-500 hover:text-gray-700">
              <Pencil size={18} />
            </button>
          )}
        </div>

        {/* Поля профиля */}
        <form onSubmit={handleSubmit(async (formData) => {
          await onUpdate(formData);
          setIsEditing(false);
        })} className="space-y-4">

          <ProfileField
            label="Никнейм"
            value={data.username}
            editable={isEditing}
            register={register("username")}
          />
          <ProfileField
            label="Email"
            value={data.email}
            editable={isEditing}
            register={register("email")}
          />
          <ProfileField
            label="Telegram"
            value={data.telegram}
            editable={isEditing}
            register={register("telegram")}
          />

          {isEditing && (
            <div className="flex gap-2 mt-4">
              <button type="submit" className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition">
                Сохранить
              </button>
              <button type="button" onClick={handleCancel} className="px-4 py-2 border rounded-lg hover:bg-gray-100">
                Отменить
              </button>
            </div>
          )}
        </form>
      </div>

      {/* Кнопки снизу */}
      <div className="flex justify-between mt-6">
        <button 
          type="button" 
          onClick={onLogout}
          className="px-4 py-2 border rounded-lg hover:bg-gray-100 transition"
        >
          Выйти
        </button>
        <button 
          type="button" 
          onClick={onDelete}
          className="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition"
        >
          Удалить аккаунт
        </button>
      </div>
    </div>
  );
};

interface ProfileFieldProps {
  label: string;
  value?: string;
  editable: boolean;
  register: any;
}

const ProfileField = ({ label, value, editable, register }: ProfileFieldProps) => {
  return (
    <div>
      <label className="block text-sm font-medium text-gray-700 mb-1">{label}</label>
      {editable ? (
        <input
          {...register}
          className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      ) : (
        <div className="w-full px-3 py-2 border rounded-lg bg-gray-50 text-gray-800">
          {value || "—"}
        </div>
      )}
    </div>
  );
};
