# Stage 1: Build JAR
FROM gradle:8.13-jdk21 AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

# Stage 2: Run JAR
FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
