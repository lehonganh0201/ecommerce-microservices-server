FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/config-server.jar /app/config-server.jar
ENTRYPOINT ["java", "-jar", "config-server.jar"]
EXPOSE 8888