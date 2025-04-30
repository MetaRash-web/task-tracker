import { useNotifications } from '@/features/websocket/NotificationProvider';

interface NotificationsModalProps {
    onClose: () => void;
  }

export default function NotificationsModal({ onClose }: NotificationsModalProps) {
  const { notifications } = useNotifications();
  console.log('notifications: ', notifications)

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div 
        className="absolute inset-0 bg-black/50" 
        onClick={onClose}
      />
      <div className="relative bg-white rounded-2xl w-full max-w-md p-6 shadow-2xl">
        <h2 className="font-bold mb-2">Уведомления</h2>
        <ul>
        {notifications.map((n) => (
          <li key={n.taskId} className="mb-1">{n.message}</li>
        ))}
        </ul>
      </div>
    </div>
  );
}
