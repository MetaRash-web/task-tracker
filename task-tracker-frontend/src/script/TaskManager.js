import { UIManager } from "./UIManager.js";
import { ModalManager } from "./ModalManager.js";
import { AuthManager } from "./AuthManager.js";

export class TaskManager {
    static tasks = [];

    static async loadTasks() {
        try {
            const response = await fetch('/tasks', {
                method: 'GET',
                headers: { 'Authorization': `Bearer ${localStorage.getItem("accessToken")}` }
            });
            TaskManager.tasks = await response.json();
            UIManager.displayTasks(TaskManager.tasks);
        } catch (error) {
            console.error('Ошибка загрузки задач:', error);
        }
    }

    static async updateTaskStatus(taskId, status) {
        const task = TaskManager.tasks.find(t => t.id === taskId);
        if (task) {
            task.status = status;
            if (status === 'COMPLETED') {
                TaskManager.tasks = TaskManager.tasks.filter(t => t.id !== taskId);
            }
            UIManager.displayTasks(TaskManager.tasks);  // Обновляем отображение задач
            console.log(`Задача ${taskId} обновлена до статуса ${status}`);
        }
    }

    static async handleTaskSubmit(event) {
        event.preventDefault();
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;
        const dueDate = document.getElementById('dueDate').value;
        const taskData = { title, description, dueDate };
        try {
            const response = await fetch('/tasks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${AuthManager.token}`,
                },
                body: JSON.stringify(taskData)
            });
            const data = await response.json();
            if (response.ok) {
                ModalManager.toggleModal('taskModal');
                alert('Задача успешно создана!');
            } else {
                alert(data.message || 'Не удалось создать задачу');
            }
        } catch (error) {
            console.error('Ошибка при создании задачи:', error);
            alert('Ошибка при создании задачи');
        }
    }

    static clearTasks() {
        this.tasks = []; // Очищаем задачи
        UIManager.displayTasks(this.tasks); // Перерисовываем пустой список
    }
}