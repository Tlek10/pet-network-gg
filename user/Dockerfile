FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/user-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]