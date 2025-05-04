import { useNotifications } from '@/features/websocket/NotificationProvider';
import { HiInformationCircle, HiTrash } from 'react-icons/hi';
import {useEffect, useState} from 'react';
import { TaskService } from '@/api/tasks/tasks';

interface NotificationsModalProps {
    onClose: () => void;
}

export default function NotificationsModal({ onClose }: NotificationsModalProps) {
  const { notifications } = useNotifications();
  const [localNotifications, setLocalNotifications] = useState(notifications);
  console.log('notifications: ', notifications)

    useEffect(() => {
        setLocalNotifications(notifications);
    }, [notifications]);

  const handleDelete = async (taskId: number) => {
    setLocalNotifications((prev) => prev.filter((n) => n.taskId !== taskId));
    try {
      await TaskService.updateTask({id: taskId, notification_sent: new Date().toISOString() });
    } catch (e) {
      setLocalNotifications((prev) => [...prev, notifications.find((n) => n.taskId === taskId)!]);
      alert('Ошибка при удалении уведомления');
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div 
        className="absolute inset-0 bg-black/50 transition-opacity duration-300" 
        onClick={onClose}
      />
      <div className="relative bg-white rounded-2xl w-full max-w-md p-6 shadow-2xl animate-fade-in">
        <h2 className="font-bold mb-4 text-lg">Уведомления</h2>
        <ul>
          {localNotifications.length === 0 && (
            <li className="text-gray-400 text-center py-6">Нет новых уведомлений</li>
          )}
          {localNotifications.map((n) => (
            <li
              key={n.taskId}
              className="flex items-start mb-3 last:mb-0 rounded-xl p-4 border-l-4 border-blue-300 shadow-sm bg-blue-50 transition-all duration-300"
            >
              <HiInformationCircle className="text-blue-400 w-6 h-6 mr-3 mt-1 flex-shrink-0" />
              <div className="flex-1">
                <div className="font-semibold text-blue-900 mb-1">{n.title}</div>
                <div className="text-sm text-gray-800 mb-1">{n.message}</div>
                {n.dueDate && (
                  <div className="text-xs text-gray-500">Срок: {new Date(n.dueDate).toLocaleString()}</div>
                )}
              </div>
              <button
                className="ml-2 p-2 rounded hover:bg-red-100 text-red-400 hover:text-red-600 transition"
                title="Удалить уведомление"
                onClick={() => handleDelete(n.taskId)}
              >
                <HiTrash className="w-5 h-5" />
              </button>
            </li>
          ))}
        </ul>
      </div>
      <style>{`
        @keyframes fade-in {
          from { opacity: 0; transform: translateY(20px); }
          to { opacity: 1; transform: translateY(0); }
        }
        .animate-fade-in {
          animation: fade-in 0.4s cubic-bezier(.4,0,.2,1);
        }
      `}</style>
    </div>
  );
}
