document.addEventListener('DOMContentLoaded', () => {
    const state = {
        isDarkTheme: false,
        token: localStorage.getItem("userToken") || null,
    };
    const storedToken = localStorage.getItem("accessToken");
    const storedUserData = localStorage.getItem("userData");
    if (storedToken && storedUserData) {const userData = JSON.parse(storedUserData);
        updateUIAfterLogin(userData);
        loadTasks().then(tasks => {
            if (tasks) {displayTasks(tasks);} else {
                console.error('Задачи не загружены.');}});} else {updateUiAfterLogout();}
    const editModal = document.getElementById('editModal');
    const closeModal = document.getElementById('closeModal');
    let currentTaskId = null;function openModal(taskId) {
        currentTaskId = taskId;
        editModal.style.display = 'flex';}closeModal.addEventListener('click', () => {
        editModal.style.display = 'none';});document.querySelectorAll('.status-btn').forEach(button => {
        button.addEventListener('click', () => {
            const status = button.getAttribute('data-status');
            updateTaskStatus(currentTaskId, status);
            editModal.style.display = 'none';
        });
    });function updateTaskStatus(taskId, status) {
        const task = tasks.find(t => t.id === taskId);
        if (task) {
            task.status = status;

            if (status === 'COMPLETED') {tasks = tasks.filter(t => t.id !== taskId);}
            displayTasks(tasks); // Обновляем отображение
            console.log(`Задача ${taskId} обновлена до статуса ${status}`);}}
    const userContainer = document.getElementById('user-container')
    const taskContainer = document.getElementById('tasksContainer')
    taskContainer.addEventListener('click', function(event) {
        const taskElement = event.target.closest('.task');
        if (taskElement) {
            const taskId = taskElement.getAttribute('data-id'); // Получаем ID задачи
            openModal(taskId);
        }
    });
    initializeUI();
    initializeEventListeners();
    function initializeUI() {
        initializeTheme(state);
    }function initializeEventListeners() {
        document.getElementById("avatar")
            .addEventListener('click', () => toggleVisibility(userContainer))
        document.getElementById('signup-form')
            .addEventListener('submit', handleSignup);
        document.getElementById('login-form')
            .addEventListener('submit', handleLogin);
        document.getElementById('logout-button')
            .addEventListener('click', () => handleLogout());
        document.getElementById('add-task-button')
            .addEventListener('click', () => openTaskModal());
        document.getElementById('show-tasks')
            .addEventListener('click', loadTasks)}
    async function handleSignup(event) {event.preventDefault();
        const formData = new FormData(event.target);
        try {const response = await fetch('/user', {
                method: 'POST',
                body: JSON.stringify(Object.fromEntries(formData)),
                headers: { 'Content-Type': 'application/json' },
            });
            const data = await response.json();
            console.log('Успешный ответ:', data);} catch (error) {
            console.error('Error:', error);
        }}async function handleLogin(event) {
        event.preventDefault(); const formData = new FormData(event.target);
        try {const response = await fetch('/auth/login', {
                method: 'POST',
                body: JSON.stringify(Object.fromEntries(formData)),
                headers: { 'Content-Type': 'application/json' }})
            const text = await response.text();
            console.log('Response Text:', text);const result = JSON.parse(text);
            if (response.ok) {
                localStorage.setItem("accessToken", result.token); // access token
                localStorage.setItem("refreshToken", result.refreshToken);  // refresh token
                localStorage.setItem("userData", JSON.stringify(result.user));
                state.token = result.token;
                console.log("result token: " + result.token, "| refresh token: " + result.refreshToken);
                await loadTasks();
                updateUIAfterLogin(result.user);} else {console.error(`Ошибка: ${result.message}`);}} catch (error) {
            console.error('Error:', error);}
    }function handleLogout() {
        state.token = null;
        localStorage.removeItem("userToken");
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken")
        document.getElementById("account-info").style.display = "none";
        document.getElementById("form-container").style.display = "block";
        document.getElementById('login-form').reset();
        document.getElementById('signup-form').reset();
        window.location.reload();
    }function toggleVisibility(element) {
        element.style.display = element.style.display === 'block' ? 'none' : 'block';}
    function updateUIAfterLogin(userData) {
        document.getElementById("form-container").style.display = "none";
        document.getElementById("account-info").style.display = "block";
        document.getElementById("username").textContent = userData.username || 'пользователь';
        document.getElementById("user-email").textContent = userData.email;
    }function updateUiAfterLogout() {
        document.getElementById("form-container").style.display = "block";
        document.getElementById("account-info").style.display = "none";}
    function displayTasks(tasks) {
        const tasksContainer = document.getElementById('tasksContainer');
        tasksContainer.innerHTML = '';
        tasks.forEach(task => {
            const taskElement = document.createElement('div');
            taskElement.className = `task ${task.status.toLowerCase()}`;
            taskElement.setAttribute('data-id', task.id);
            taskElement.innerHTML  = `...`;
            tasksContainer.appendChild(taskElement);
        });
    }function openTaskModal() {
        const modal = document.getElementById('taskModal');
        modal.style.display = 'block';
    }function closeTaskModal() {
        const modal = document.getElementById('taskModal');
        modal.style.display = 'none';
    }async function loadTasks() {
        try {const response = await fetch('/tasks', {
                method: 'GET',
                headers: { 'Authorization': `Bearer ${localStorage.getItem("accessToken")}` }});
            const responseText = await response.text();
            if (!response.ok) {
                const errorData = response.headers.get('Content-Type').includes('application/json')
                    ? await response.json()
                    : { message: 'Не удалось загрузить задачи' };
                throw new Error(errorData.message || 'Не удалось загрузить задачи');}
            const tasks = JSON.parse(responseText);
            console.log("Parsed tasks: ", tasks);
            displayTasks(tasks);} catch (error) {console.error('Ошибка загрузки задач:', error);
        }}async function handleTaskSubmit(event) {event.preventDefault();
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;
        const dueDate = document.getElementById('dueDate').value;
        const taskData = {
            title: title,
            description: description,
            dueDate: dueDate,};try {const response = await fetch('/tasks', {
                method: 'POST', headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${state.token}`,}, body: JSON.stringify(taskData)});
            const data = await response.json();if (response.ok) {
                closeTaskModal();
                alert('Task created successfully!');} else {
                alert(data.message || 'Failed to create task');}} catch (error) {
            console.error('Error creating task:', error);
            alert('Error creating task');}}function initializeTheme(state) {
        state.isDarkTheme = localStorage.getItem('isDarkTheme') === 'true';
        updateTheme(state.isDarkTheme);
        const checkbox = document.querySelector('.theme-toggle');
        checkbox.checked = state.isDarkTheme;
        checkbox.addEventListener('change', () => toggleTheme(state));}function updateTheme(isDark) {
        const body = document.body;
        if (isDark) {
            body.classList.add('dark-theme');
            body.classList.remove('light-theme');} else {
            body.classList.add('light-theme');
            body.classList.remove('dark-theme');
        }}function toggleTheme(state) {
        state.isDarkTheme = !state.isDarkTheme;
        localStorage.setItem('isDarkTheme', state.isDarkTheme);
        updateTheme(state.isDarkTheme);}});