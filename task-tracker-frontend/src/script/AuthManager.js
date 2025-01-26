import {TaskManager} from "./TaskManager.js";
import {UIManager} from "./UIManager.js";

export class AuthManager {
    static token = null;
    constructor() {
        this.token = localStorage.getItem('accessToken') || null;
    }

    async handleSignup(event) {
        event.preventDefault();
        const formData = new FormData(event.target);
        try {
            const response = await fetch('/user', {
                method: 'POST',
                body: JSON.stringify(Object.fromEntries(formData)),
                headers: { 'Content-Type': 'application/json' },
            });
            const data = await response.json();
            console.log('Успешный ответ:', data);
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }

    async handleLogin(event) {
        event.preventDefault();
        const formData = new FormData(event.target);
        try {
            const response = await fetch('/auth/login', {
                method: 'POST',
                body: JSON.stringify(Object.fromEntries(formData)),
                headers: { 'Content-Type': 'application/json' }
            });
            const result = await response.json();
            if (response.ok) {
                localStorage.setItem("accessToken", result.token);
                console.log('access token: ', result.token)
                localStorage.setItem("refreshToken", result.refreshToken);
                localStorage.setItem("userData", JSON.stringify(result.user));
                this.token = result.token;
                UIManager.setUi(result.user);
                await TaskManager.loadTasks();
            } else {
                console.error(`Ошибка: ${result.message}`);
            }
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }

    handleLogout() {
        this.token = null;
        localStorage.removeItem("userToken");
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("userData")
        TaskManager.clearTasks();
        UIManager.setUi(null);

        window.onreset;
    }
}