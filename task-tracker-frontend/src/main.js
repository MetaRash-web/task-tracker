import { UIManager } from './script/UIManager.js';
import {AuthManager} from "./script/AuthManager.js";
import {ModalManager} from "./script/ModalManager.js";
import {TaskManager} from "./script/TaskManager.js";
import {EmailSenderManager} from "./script/EmailSenderManager.js";

let currentTaskId = null;

document.addEventListener('DOMContentLoaded', () => {

    if (localStorage.getItem('accessToken')) {
        UIManager.initializeUI();
        UIManager.setUi(JSON.parse(localStorage.getItem('userData')));
        TaskManager.renderTasks();
    } else {
        UIManager.setUi(null);
    }

    // Инициализация событий
    document.getElementById('signup-form').addEventListener('submit', (event) => new AuthManager().handleSignup(event));
    document.getElementById('login-form').addEventListener('submit', (event) => new AuthManager().handleLogin(event));
    document.getElementById('logout-button').addEventListener('click', () => new AuthManager().handleLogout());
    document.getElementById('avatar').addEventListener('click', () => ModalManager.toggleModal('user-container'))
    document.getElementById('close-edit-button').addEventListener('click', () => ModalManager.toggleModal('editModal'))
    document.getElementById('edit-button').addEventListener('click', (event) => new TaskManager().updateTask(event))
    document.getElementById('delete-confirm-button').addEventListener('click', (event) => new TaskManager().deleteTask(event))
    document.getElementById('delete-cancel-button').addEventListener('click', () => ModalManager.toggleModal('deleteModal'));
    document.getElementById('send-email').addEventListener('click', (event) => new EmailSenderManager().sendEmail(event))

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