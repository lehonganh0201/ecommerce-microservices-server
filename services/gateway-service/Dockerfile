# Sử dụng image OpenJDK 17 slim để tối ưu kích thước
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file JAR từ thư mục target
COPY target/gateway-service.jar /app/gateway-service.jar

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]
# Mở cổng mặc định của Spring Cloud Gateway
EXPOSE 8899