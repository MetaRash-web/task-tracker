import { Client, IFrame } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

let stompClient: Client | null = null;

export const connectToNotifications = (
    username: string,
    onMessage: (message: any) => void
) => {
    const socket = new SockJS('http://localhost:8080/ws');

    stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: (frame: IFrame) => {
            console.log('[WebSocket] Connected! Username:', username);
            stompClient?.subscribe(
                `/user/topic/notifications`,
                (message) => {
                    try {
                        const parsed = JSON.parse(message.body);
                        onMessage(parsed);
                    } catch (e) {
                        console.error('Error parsing message:', e);
                    }
                }
            );
        },
        onStompError: (frame: IFrame) => {
            console.error('[WebSocket] Protocol error:', frame.headers.message);
        },
        onDisconnect: (frame: IFrame) => {
            console.log('[WebSocket] Disconnected');
        },
        onWebSocketError: (event: Event) => {
            console.error('[WebSocket] Connection error:', event);
        }
    });

    // 10. Активация подключения
    stompClient.activate();
};

export const disconnectFromNotifications = () => {
    if (stompClient?.connected) {
        stompClient.deactivate().then(() => {
            console.log('[WebSocket] Disconnected');
            stompClient = null;
        });
    }
};