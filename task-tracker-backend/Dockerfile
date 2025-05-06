# Stage 1: Build the jar (multi-module build from monorepo root)
FROM maven:3.9.6-eclipse-temurin-22 AS build
WORKDIR /app
COPY .. .
RUN mvn clean package -pl task-tracker-backend -am -DskipTests

# Stage 2: Minimal runtime image
FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /app/task-tracker-backend/target/task-tracker-backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]