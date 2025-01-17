document.addEventListener('DOMContentLoaded', () => {
    const state = {
        isDarkTheme: false,
        token: localStorage.getItem("userToken") || null,
    };

    const storedToken = localStorage.getItem("userToken");
    const storedUserData = localStorage.getItem("userData");

    if (storedToken && storedUserData) {
        const userData = JSON.parse(storedUserData);
        updateUIAfterLogin(userData);
        loadTasks().then(tasks => {
            if (tasks) {
                displayTasks(tasks);
            } else {
                console.error('Задачи не загружены.');
            }
        });
    } else {
        updateUiAfterLogout();
    }

    const userContainer = document.getElementById("user-container")

    initializeUI();
    initializeEventListeners();

    function initializeUI() {
        initializeTheme(state);
    }

    function initializeEventListeners() {
        document.getElementById("avatar")
            .addEventListener('click', () => toggleVisibility(userContainer))
        console.log("Listener added for avatar");

        document.getElementById('signup-form')
            .addEventListener('submit', handleSignup);
        console.log("Listener added for signup form");

        document.getElementById('login-form')
            .addEventListener('submit', handleLogin);
        console.log("Listener added for login form");

        document.getElementById('logout-button')
            .addEventListener('click', () => handleLogout());
        console.log("Listener added for logout button");

        document.getElementById('add-task-button')
            .addEventListener('click', () => openTaskModal);
        console.log("Listener added for add task button");
    }

    async function handleSignup() {
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
            console.error('Error:', error);
        }
    }

    async function handleLogin() {
        event.preventDefault();
        const formData = new FormData(event.target);
        try {
            const response = await fetch('/auth/login', {
                method: 'POST',
                body: JSON.stringify(Object.fromEntries(formData)),
                headers: { 'Content-Type': 'application/json' }
            }).then(response => response.text())  // Прочитаем ответ как текст
                .then(data => {
                    console.log(data);  // Логируем ответ
                    try {
                        const jsonData = JSON.parse(data);
                        console.log(jsonData);  // Дальше работаем с JSON
                    } catch (e) {
                        console.error('Ошибка парсинга JSON:', e);
                    }
                })
                .catch(error => console.error('Ошибка запроса:', error));
            const result = await response.json();

            console.log("result: " + result)

            if (response.ok) {
                localStorage.setItem("accessToken", result.token);
                localStorage.setItem("refreshToken", result.refreshToken);  // refresh token
                localStorage.setItem("userData", JSON.stringify(result.user));
                state.token = result.token;  // Записываем access token в state
                console.log("result token: " + result.token, "| refresh token: " + result.refreshToken);
                await loadTasks();
                updateUIAfterLogin(result.user);
            } else {
                console.error(`Ошибка: ${result.message}`);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }

    function handleLogout() {
        state.token = null;
        localStorage.removeItem("userToken");

        document.getElementById("account-info").style.display = "none";
        document.getElementById("form-container").style.display = "block";

        document.getElementById('login-form').reset();
        document.getElementById('signup-form').reset();

        window.location.reload();
    }

    function toggleVisibility(element) {
        element.style.display = element.style.display === 'block' ? 'none' : 'block';
    }

    function updateUIAfterLogin(userData) {
        document.getElementById("form-container").style.display = "none";
        document.getElementById("account-info").style.display = "block";

        document.getElementById("username").textContent = userData.username || 'пользователь';
        document.getElementById("user-email").textContent = userData.email;
    }

    function updateUiAfterLogout() {
        document.getElementById("form-container").style.display = "block";
        document.getElementById("account-info").style.display = "none";
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

    function openTaskModal() {
        const modal = document.getElementById('taskModal');
        modal.style.display = 'block';
    }

    function closeTaskModal() {
        const modal = document.getElementById('taskModal');
        modal.style.display = 'none';
    }

    async function loadTasks() {
        try {
            console.log("access token: " + localStorage.getItem("accessToken"))
            const response = await fetch('/tasks', {
                method: 'GET',
                headers: { 'Authorization': `Bearer ${localStorage.getItem("accessToken")}` }
            });
            const responseText = await response.text();
            console.log("Response text: ", responseText);

            if (!response.ok) {
                const errorData = JSON.parse(responseText);
                throw new Error(errorData.message || 'Не удалось загрузить задачи');
            }

            const tasks = JSON.parse(responseText);
            console.log("Parsed tasks: ", tasks);

            displayTasks(tasks);
        } catch (error) {
            console.error('Ошибка загрузки задач:', error);
        }
    }

    async function handleTaskSubmit(event) {
        event.preventDefault();
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;
        const dueDate = document.getElementById('dueDate').value;

        const taskData = {
            title: title,
            description: description,
            dueDate: dueDate,
        };

        try {
            const response = await fetch('/tasks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${state.token}`,
                },
                body: JSON.stringify(taskData)
            });

            const data = await response.json();

            if (response.ok) {
                closeTaskModal();
                alert('Task created successfully!');
            } else {
                alert(data.message || 'Failed to create task');
            }
        } catch (error) {
            console.error('Error creating task:', error);
            alert('Error creating task');
        }
    }

    function initializeTheme(state) {
        state.isDarkTheme = localStorage.getItem('isDarkTheme') === 'true';
        updateTheme(state.isDarkTheme);
        const checkbox = document.querySelector('.theme-toggle');
        checkbox.checked = state.isDarkTheme;
        checkbox.addEventListener('change', () => toggleTheme(state));
    }

    function updateTheme(isDark) {
        const body = document.body;
        if (isDark) {
            body.classList.add('dark-theme');
            body.classList.remove('light-theme');
        } else {
            body.classList.add('light-theme');
            body.classList.remove('dark-theme');
        }
    }

    function toggleTheme(state) {
        state.isDarkTheme = !state.isDarkTheme;
        localStorage.setItem('isDarkTheme', state.isDarkTheme);
        updateTheme(state.isDarkTheme);
    }
});