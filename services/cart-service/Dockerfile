# Sử dụng image OpenJDK 17 slim để tối ưu kích thước
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file JAR từ thư mục target
COPY target/cart-service.jar /app/cart-service.jar

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "cart-service.jar"]

# Mở cổng mặc định của Spring Boot
EXPOSE 8087