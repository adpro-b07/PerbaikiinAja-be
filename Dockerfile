FROM gradle:8.3-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
