document.addEventListener('DOMContentLoaded', () => {
    const state = {
        isDarkTheme: false,
        token: null,
    };

    let formContainer = document.getElementById("form-container")
    let avatar = document.getElementById("avatar")

    document.getElementById('task-form')
        .addEventListener('submit', (event) => handleTaskSubmit(event));
    console.log("Listener added for task form");

    avatar.addEventListener('click', () => openFormContainer())

    function openFormContainer() {
        formContainer.style.display = 'block';
    }

    document.getElementById('login-form')
        .addEventListener('submit', handleLogin);

    async function handleLogin() {
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
                state.token = result.token;
                updateUIAfterLogin(result.user);
                await loadTasks();
            } else {
                console.error(`Ошибка: ${result.message}`);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }

    function updateUIAfterLogin(userData) {
        formContainer.innerHTML = `
        <div class="account-info">
            <h2>Привет, ${userData.username || 'пользователь'}!</h2>
            <p>Email: ${userData.email}</p>
            <button id="logout-button">Выйти</button>
        </div>
    `;
    }

    async function loadTasks() {
        try {
            const response = await fetch('/tasks', {
                method: 'GET',
                headers: { 'Authorization': `Bearer ${state.token}` }
            });
            const tasks = await response.json();
            displayTasks(tasks);
        } catch (error) {
            console.error('Ошибка загрузки задач:', error);
        }
    }

    function displayTasks(tasks) {
        const tasksContainer = document.getElementById('tasksContainer');
        tasksContainer.innerHTML = '';
        tasks.forEach(task => {
            const taskElement = document.createElement('div');
            taskElement.className = 'task';
            taskElement.innerHTML = `<h3>${task.title}</h3><p>${task.description}</p>`;
            tasksContainer.appendChild(taskElement);
        });
    }
});