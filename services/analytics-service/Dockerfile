FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/config-server-3.4.3.jar /app/config-server.jar
ENTRYPOINT ["java", "-jar", "config-server.jar"]
EXPOSE 8888