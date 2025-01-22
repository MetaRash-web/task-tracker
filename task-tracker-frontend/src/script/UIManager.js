import {ThemeManager} from "./ThemeManager.js";

export class UIManager {
    static initializeUI() {
        new ThemeManager();
    }

    static setUiUserContainer(userData) {
        if(userData) {
            document.getElementById("form-container").style.display = "none";
            document.getElementById("account-info").style.display = "block";
            document.getElementById("username").textContent = userData.username || 'пользователь';
            document.getElementById("user-email").textContent = userData.email;
        } else {
            document.getElementById("form-container").style.display = "block";
            document.getElementById("account-info").style.display = "none";
        }
    }

    static displayTasks(tasks) {
        const tasksContainer = document.getElementById('tasksContainer');
        tasksContainer.innerHTML = '';
        tasks.forEach(task => {
            const taskElement = document.createElement('div');
            taskElement.className = `task ${task.status.toLowerCase()}`;
            taskElement.setAttribute('data-id', task.id);
            taskElement.innerHTML = `
            <div class="task-header">
                <h3>${task.title}</h3>
            </div>
            <p class="task-description">${task.description}</p>
            <div class="task-footer">
                <span class="task-date">Due: ${task.dueDate}</span>
                <button class="task-edit-btn">Edit</button>
            </div>
            `;
            tasksContainer.appendChild(taskElement);
        });
    }
}