"use client"

import { useEffect, useState } from 'react';
import { useAuth } from '@/features/auth/hooks/use-auth';
import { connectToNotifications, disconnectFromNotifications } from '../notifications';
import { NotificationMessage } from '@/types/notification.types';

export function useNotificationSocket() {
  const { user } = useAuth();
  const [notifications, setNotifications] = useState<NotificationMessage[]>([]);
  
  useEffect(() => {
    if (!user?.username) return;

    connectToNotifications(user.username, (msg) => {
      setNotifications((prev) => [msg, ...prev]);
    });

    return () => {
      disconnectFromNotifications();
    };
  });

  return { notifications };
}