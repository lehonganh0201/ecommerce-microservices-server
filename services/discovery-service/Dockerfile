FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/discovery-service.jar /app/discovery-service.jar
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]
EXPOSE 8761