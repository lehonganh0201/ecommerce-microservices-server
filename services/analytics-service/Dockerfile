FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/analytics-service.jar /app/analytics-service.jar
ENTRYPOINT ["java", "-jar", "analytics-server.jar"]
EXPOSE 8088