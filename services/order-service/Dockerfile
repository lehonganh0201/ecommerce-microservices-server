# Sử dụng image OpenJDK 17 slim để tối ưu kích thước
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR từ thư mục target
COPY target/order-service.jar /app/order-service.jar

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "order-service.jar"]

# Mở cổng mặc định của Spring Boot
EXPOSE 8085