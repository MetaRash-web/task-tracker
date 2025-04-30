import { Client, IMessage } from '@stomp/stompjs';

let stompClient: Client | null = null;

export const connectToNotifications = (
  username: string,
  onMessage: (message: any) => void
) => {
  const socket = new WebSocket('ws://localhost:8080/ws');
  
  stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
          console.log('[WebSocket] Connected!');
          stompClient?.subscribe('/topic/notifications', (message) => {
            const parsed = JSON.parse(message.body);
            onMessage(parsed);
        });
      },
      onStompError: (frame) => {
          console.error('[WebSocket] Error:', frame.headers.message);
      }
  });

  stompClient.activate();
};

export const disconnectFromNotifications = () => {
  if (stompClient?.connected) {
    stompClient.deactivate();
    console.log('[WebSocket] Disconnected');
  }
};
