.PHONY: build run stop

build:
	@echo "🛠️  Билдим бэкенд..."
	cd task-tracker-backend && mvn clean package -DskipTests
	@echo "🛠️  Билдим email-сервис..."
	cd task-tracker-email-sender && mvn clean package -DskipTests
	@echo "🛠️  Билдим scheduler..."
	cd task-tracker-scheduler && mvn clean package -DskipTests
	@echo "️  Билдим frontend..."
	cd task-tracker-frontend && npm run build

run:
	@echo "🚀 Запускаем сервисы..."
	docker-compose up --build

stop:
	@echo "🛑 Останавливаем сервисы..."
	docker compose down