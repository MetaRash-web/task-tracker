'use client';

import { ReactNode, createContext, useContext } from 'react';
import { useNotificationSocket } from './hooks/useNotificationSocket';
import { NotificationMessage } from '@/types/notification.types';

interface NotificationContextValue {
    notifications: NotificationMessage[];
  }
  
  export const NotificationContext = createContext<NotificationContextValue | null>(null);
export const NotificationProvider = ({ children }: { children: ReactNode }) => {
  const { notifications } = useNotificationSocket();

  return (
    <NotificationContext.Provider value={{ notifications }}>
      {children}
    </NotificationContext.Provider>
  );
};

export const useNotifications = () => {
    const ctx = useContext(NotificationContext);
    if (!ctx) throw new Error('useNotifications must be used within NotificationProvider');
    return ctx;
  };