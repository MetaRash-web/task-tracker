import { UIManager } from './script/UIManager.js';
import {AuthManager} from "./script/AuthManager.js";
import {ModalManager} from "./script/ModalManager.js";
import {TaskManager} from "./script/TaskManager.js";

let currentTaskId = null;

document.addEventListener('DOMContentLoaded', () => {

    UIManager.initializeUI();// Инициализация UI
    UIManager.setUiUserContainer(JSON.parse(localStorage.getItem('userData')));

    // Инициализация событий
    document.getElementById('signup-form').addEventListener('submit', (event) => new AuthManager().handleSignup(event));
    document.getElementById('login-form').addEventListener('submit', (event) => new AuthManager().handleLogin(event));
    document.getElementById('logout-button').addEventListener('click', () => new AuthManager().handleLogout());
    document.getElementById('avatar').addEventListener('click', () => ModalManager.toggleModal('user-container'))

    // Подключаем события для работы с задачами
    document.getElementById('add-task-button').addEventListener('click', () => ModalManager.toggleModal('taskModal'));
    document.getElementById('show-tasks').addEventListener('click', () => TaskManager.loadTasks());
    document.getElementById('taskModal').addEventListener('submit', (event) => TaskManager.handleTaskSubmit(event));

    document.querySelectorAll('.status-btn').forEach(button => {
        button.addEventListener('click', () => {
            const status = button.getAttribute('data-status');
            console.log('current task id: ' + currentTaskId)
            TaskManager.updateTaskStatus(currentTaskId, status).then(() => null);
            ModalManager.toggleModal('editModal');
        });
    });
});