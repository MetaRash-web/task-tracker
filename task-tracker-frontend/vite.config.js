import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/auth': 'http://localhost:8080',
      '/user': 'http://localhost:8080',
      '/tasks': 'http://localhost:8080',
      '/send-email': 'http://localhost:8080'
    },
    port: 80,
    strictPort: true,
  },
})
