# Sử dụng image OpenJDK 17 slim để tối ưu kích thước
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file JAR từ thư mục target
COPY target/notification-service.jar /app/notification-service.jar

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
# Mở cổng mặc định của Spring Boot
EXPOSE 8080