# Stage 1: Build JAR
FROM gradle:8.3-jdk17 AS builder
WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# Stage 2: Run JAR
FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
