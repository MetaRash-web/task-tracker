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
            TaskManager.renderTasks();
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
            TaskManager.renderTasks();
            console.log(`Задача ${taskId} обновлена до статуса ${status}`);
        }
    }

    static async handleTaskSubmit(event) {
        event.preventDefault();
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;
        const dueDate = document.getElementById('dueDate').value;
        const status = 'PENDING';
        const taskData = { description, dueDate, title, status };
        try {
            const response = await fetch('/tasks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
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

    static renderTask(task) {
        const taskElement = document.createElement('div');
        taskElement.classList.add('task');

        taskElement.innerHTML = `
        <div class="task-header">
            <h3>${task.title}</h3>
        </div>
        <p class="task-description">${task.description || 'No description provided'}</p>
        <div class="task-footer">
            <span class="task-date">Due: ${new Date(task.dueDate).toLocaleString()}</span>
            <button class="task-edit-btn" data-task-id="${task.id}">Edit</button>
        </div>
            `;

        const editButton = taskElement.querySelector('.task-edit-btn');
        editButton.addEventListener('click', () => {
            TaskManager.editTask(task.id);
        });

        return taskElement;
    }

    static renderTasks() {
        const tasksContainer = document.getElementById('tasksContainer');
        tasksContainer.innerHTML = '';

        TaskManager.tasks.forEach(task => {
            const taskElement = TaskManager.renderTask(task);
            tasksContainer.appendChild(taskElement);
        });
    }

    static clearTasks() {
        const tasksContainer = document.getElementById('tasksContainer')
        tasksContainer.innerHTML = '';
    }

    static editTask(taskId) {
        const task = TaskManager.tasks.find(t => t.id === taskId);
        if (task) {
            document.getElementById('edit-title').value = task.title;
            document.getElementById('edit-description').value = task.description;
            document.getElementById('edit-dueDate').value = task.dueDate;
            document.getElementById('edit-form').dataset.taskId = taskId;
            ModalManager.toggleModal('editModal');
        } else {
            console.error(`Задача с ID ${taskId} не найдена.`);
        }
    }

    async updateTask(event) {
        event.preventDefault();

        const taskId = document.getElementById('edit-form').dataset.taskId;
        const updatedTask = {
            id: taskId,
            title: document.getElementById('edit-title').value,
            description: document.getElementById('edit-description').value,
            dueDate: document.getElementById('edit-dueDate').value
        };
        try {
            const response = await fetch('/tasks/' + taskId, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                },
                body: JSON.stringify(updatedTask)
            });
            const data1 = await response.text();
            console.log(data1)
            const data = JSON.parse(data1)
            if (response.ok) {
                ModalManager.toggleModal('editModal');
                alert('Задача успешно обновлена!');
                TaskManager.renderTasks();
            } else {
                alert(data.message || 'Не удалось обновить задачу');
            }
        } catch (error) {
            console.error('Ошибка при обновлении задачи:', error);
            alert('Ошибка при создании задачи');
        }
    }

    async deleteTask(event) {
        event.preventDefault();
        const taskId = document.getElementById('edit-form').dataset.taskId
        try {
            const response = await fetch('/tasks/' + taskId, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
                }
            });
            if (response.ok) {
                ModalManager.toggleModal('editModal');
                alert('Задача успешно удалена!');
                console.log(response.text())
                TaskManager.tasks = TaskManager.tasks.filter(task => task.id !== taskId);
                TaskManager.renderTasks();
            } else {
                const data = await response.json();
                alert(data.message || 'Не удалось удалить задачу');
            }
        } catch (error) {
            console.error('Ошибка при удалении задачи:', error);
            alert('Ошибка при удалении задачи');
        }
    }
}