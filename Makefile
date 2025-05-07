.PHONY: build run stop

build:
	@echo "🛠️  Билдим backend..."
	cd task-tracker-backend && mvn clean package -DskipTests
	@echo "🛠️  Билдим email-сервис..."
	cd task-tracker-email-sender && mvn clean package -DskipTests
	@echo "🛠️  Билдим scheduler..."
	cd task-tracker-scheduler && mvn clean package -DskipTests
	@echo "🚀 Запускаем сервисы..."
	docker-compose up -d --build

run:
	@echo "🚀 Запускаем сервисы..."
	docker-compose up -d --build

stop:
	@echo "🛑 Останавливаем сервисы..."
	docker compose down

backend:
	docker-compose down backend
	cd task-tracker-backend && mvn clean package -DskipTests && cd ..
	docker-compose build backend

scheduler:
	docker-compose down scheduler
	cd task-tracker-scheduler && mvn clean package -DskipTests && cd ..
	docker-compose build scheduler

email-sender:
	docker-compose down email-sender
	cd task-tracker-email-sender && mvn clean package -DskipTests && cd ..
	docker-compose build email-sender


frontend:
	docker-compose down frontend
	docker-compose build frontend