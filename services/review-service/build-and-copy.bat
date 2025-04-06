@echo off
call mvnw clean package
copy /Y target\*.jar D:\workspace\Java\ecommerce-microservices\resource\jar-file\
