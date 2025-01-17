export default {
    server: {
        proxy: {
            '/auth': 'http://localhost:8080',
            '/user': 'http://localhost:8080',
            '/tasks': 'http://localhost:8080',
            '/api': 'http://localhost:8080'
        },
        port: 80,
        strictPort: true, // Если порт занят, Vite не будет юзать другой
    },
};