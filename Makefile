.PHONY: build run stop

build:
	@echo "🛠️  Билдим backend..."
	mvn clean package -pl task-tracker-backend -DskipTests
	@echo "🛠️  Билдим email-сервис..."
	mvn clean package -pl task-tracker-email-sender -DskipTests
	@echo "🛠️  Билдим scheduler..."
	mvn clean package -pl task-tracker-scheduler -DskipTests
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
	mvn clean package -pl task-tracker-backend -am -DskipTests
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